package com.miraeldev.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.data.BuildConfig
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.di.qualifiers.AuthClient
import com.miraeldev.domain.models.auth.Token
import com.miraeldev.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


internal class UserAuthDataRepositoryImpl @Inject constructor(
    @AuthClient private val client: HttpClient,
    private val localService: LocalTokenService,
    private val localUserDataRepository: LocalUserDataRepository,
    private val userDataRepository: UserDataRepository,
    private val appDatabase: AppDatabase,
    private val context: Context
) : UserAuthDataRepository {

    private val isSignInError = MutableSharedFlow<Boolean>()
    private val isSignUpError = MutableSharedFlow<Boolean>()
    private val registrationComplete = MutableSharedFlow<Boolean>()

    override fun getSignInError(): Flow<Boolean> = isSignInError.asSharedFlow()

    override fun getSignUpError(): Flow<Boolean> = isSignUpError.asSharedFlow()
    override fun getRegistrationCompleteResult(): Flow<Boolean> =
        registrationComplete.asSharedFlow()

    override suspend fun signUp(user: User) {

        val signUpResponse = client.post {
            url(BuildConfig.AUTH_REGISTER_URL)
            setBody(
                MultiPartFormDataContent(
                    formData {

                        append("name", user.name)
                        append("email", user.email)
                        append("username", user.username)
                        append("password", user.password)

                        if (user.image != "") {
                            val uri = Uri.parse(user.image)

                            val fileBytes = File(getRealPathFromURI(uri, context) ?: "")
                                .readBytes()

                            append("file", fileBytes, Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=ok")
                            })
                        }

                    },
                )
            )
        }

        if (signUpResponse.status.value in 200..299) {
            registrationComplete.emit(true)
        } else {
            isSignUpError.emit(true)
        }
    }

    override suspend fun verifyOtpCode(user: User, otpToken: String) {

        val verifyOtpResponse = client.post {
            url(BuildConfig.AUTH_VERIFY_OTP_URL)
            headers {
                remove(HttpHeaders.Authorization)
            }

            setBody(
                mapOf(
                    Pair("otpToken", otpToken)
                )
            )
        }

        if (verifyOtpResponse.status.value in 200..299) {
            val signInResponse = client.post {
                val signInUser = User(password = user.password, username = user.username)
                url(BuildConfig.AUTH_LOGIN_URL)
                headers {
                    remove(HttpHeaders.Authorization)
                }
                setBody(signInUser)
            }
            if (signInResponse.status.isSuccess()) {
                logIn(response = signInResponse)
            }
        } else {
            isSignUpError.emit(true)
        }
    }

    override suspend fun signIn(username: String, password: String) {
        val user = User(username = username, password = password)

        val response = client.post {
            url(BuildConfig.AUTH_LOGIN_URL)
            headers {
                remove(HttpHeaders.Authorization)
            }
            setBody(user)
        }

        if (response.status.isSuccess()) {
            logIn(response = response)
        } else {
            isSignInError.emit(true)
        }
    }

    override suspend fun logInWithGoogle(idToken: String) {
        val logInWithGoogleResponse = client.post {
            url(BuildConfig.GOOGLE_LOGIN)
            headers {
                remove(HttpHeaders.Authorization)
            }

            setBody(
                mapOf(
                    Pair("idToken", idToken)
                )
            )
        }

        if (logInWithGoogleResponse.status.isSuccess()) {
            logIn(response = logInWithGoogleResponse)
        }
    }

    override suspend fun checkAuthState() {
        if (localService.getBearerToken().isNullOrEmpty() ||
            localService.getRefreshToken().isNullOrEmpty()
        ) {
            localUserDataRepository.setUserUnAuthorizedStatus()
        } else {
            localUserDataRepository.setUserAuthorizedStatus()
        }
    }

    override suspend fun loginWithVk(accessToken: String, userId: String, email: String?) {
        val logInWithVkResponse = client.post {
            url(BuildConfig.VK_LOGIN)
            headers {
                remove(HttpHeaders.Authorization)
            }

            setBody(
                mapOf(
                    Pair("accessToken", accessToken),
                    Pair("userId", userId),
                    Pair("email", email),
                )
            )
        }

        if (logInWithVkResponse.status.isSuccess()) {
            logIn(response = logInWithVkResponse)
        }
    }

    override suspend fun logOutUser() {

        val refreshToken = localService.getRefreshToken()
        val response = client.post {
            url(BuildConfig.AUTH_LOGOUT_URL)
            headers {
                append(HttpHeaders.Authorization, "Bearer $refreshToken")
            }
        }
        if (response.status.isSuccess()) {
            localUserDataRepository.setUserUnAuthorizedStatus()
            delay(100)
            appDatabase.userDao().deleteOldUser()
        }
    }

    private suspend fun logIn(response: HttpResponse) {

        val token = response.body<Token>()

        localService.saveBearerToken(token.bearerToken)
        localService.saveRefreshToken(token.refreshToken)


        val isSuccess = userDataRepository.saveRemoteUser()

        if (isSuccess) {
            localUserDataRepository.setUserAuthorizedStatus()
        }


    }

    private fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = bytesAvailable.coerceAtMost(maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }

            inputStream?.close()
            outputStream.close()

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path
    }
}