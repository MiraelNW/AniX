package com.miraeldev.impl

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.miraeldev.impl.models.routes.AppNetworkRoutes
import com.miraeldev.impl.models.routes.AuthNetworkRoutes
import com.miraeldev.models.user.User
import com.pluto.plugins.network.ktor.PlutoKtorInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Inject
class AuthNetworkClientImpl(private val context: Context) : com.miraeldev.api.AuthNetworkClient {

    override val client = HttpClient(CIO).config {

        defaultRequest {
            url(AppNetworkRoutes.BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }

        install(HttpCache)

        install(PlutoKtorInterceptor)

        install(HttpRequestRetry) {
            maxRetries = 1
            retryIf { request, response ->
                !response.status.isSuccess() && response.status.value != 401
            }
            delayMillis {
                500L
            }
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("HttpClient", message)
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 2000L
            connectTimeoutMillis = 10000L
            socketTimeoutMillis = 5000L
        }
    }

    override suspend fun saveNewPassword(email: String, newPassword: String): HttpResponse =
        client.post {
            url(AuthNetworkRoutes.CREATE_NEW_PASSWORD)
            setBody(
                mapOf(
                    Pair("email", email),
                    Pair("password", newPassword),
                )
            )
        }

    override suspend fun verifyOtpForgotPassword(otp: String): HttpResponse = client.post {
        url(AuthNetworkRoutes.VERIFY_OTP_FORGOT_PASSWORD)
        setBody(
            mapOf(
                Pair("token", otp)
            )
        )
    }

    override suspend fun checkEmailExist(email: String): HttpResponse = client.post {
        url(AuthNetworkRoutes.CHECK_EMAIL)
        setBody(
            mapOf(
                Pair("email", email)
            )
        )
    }

    override suspend fun signUp(user: User): HttpResponse = client.post {
        url(AuthNetworkRoutes.AUTH_REGISTER_URL)
        setBody(
            MultiPartFormDataContent(
                formData {

                    append("name", user.name)
                    append("email", user.email)
                    append("username", user.email)
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

    override suspend fun verifyOtpCode(user: User, otpToken: String): HttpResponse =
        client.post {
            url(AuthNetworkRoutes.AUTH_VERIFY_OTP_URL)
            headers {
                remove(HttpHeaders.Authorization)
            }

            setBody(
                mapOf(
                    Pair("otpToken", otpToken)
                )
            )
        }

    override suspend fun signIn(email: String, password: String): HttpResponse =
        client.post {
            val signInUser =
                User(password = password, username = email)
            url(AuthNetworkRoutes.AUTH_LOGIN_URL)
            headers {
                remove(HttpHeaders.Authorization)
            }
            setBody(signInUser)
        }

    override suspend fun logInWithGoogle(idToken: String): HttpResponse =
        client.post {
            url(AuthNetworkRoutes.GOOGLE_LOGIN)
            headers {
                remove(HttpHeaders.Authorization)
            }

            setBody(
                mapOf(
                    Pair("idToken", idToken)
                )
            )
        }

    override suspend fun loginWithVk(
        accessToken: String,
        userId: String,
        email: String?
    ): HttpResponse =
        client.post {
            url(AuthNetworkRoutes.VK_LOGIN)
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

    override suspend fun logOutUser(refreshToken: String): HttpResponse =
        client.post {
            url(AuthNetworkRoutes.AUTH_LOGOUT_URL)
            headers {
                append(HttpHeaders.Authorization, "Bearer $refreshToken")
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