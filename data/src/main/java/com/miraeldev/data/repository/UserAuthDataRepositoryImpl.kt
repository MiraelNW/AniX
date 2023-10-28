package com.miraeldev.data.repository

import android.content.Context
import android.util.Log
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.data.BuildConfig
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.di.qualifiers.AuthClient
import com.miraeldev.user.User
import com.miraeldev.domain.models.auth.RefreshToken
import com.miraeldev.domain.models.auth.Token
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

internal class UserAuthDataRepositoryImpl @Inject constructor(
    @AuthClient private val client: HttpClient,
    private val localService: LocalTokenService,
    private val userDataRepository: UserDataRepository,
    context: Context
) : UserAuthDataRepository {

    private val isSignInError = MutableSharedFlow<Boolean>()
    private val isSignUpError = MutableSharedFlow<Boolean>()


    private val storage = VKPreferencesKeyValueStorage(context)
    private val token
        get() = VKAccessToken.restore(storage)

    override suspend fun signUp(name: String, email: String, password: String, imagePath: String) {

        val user =
            User(username = name, email = email, password = password, userImagePath = imagePath)

        val logInResponse = client.post {
            url(BuildConfig.AUTH_REGISTER_URL)
            headers {
                remove(HttpHeaders.Authorization)
            }
            setBody(user)
        }

        if (logInResponse.status.value in 200..299) {
            val response = client.post {
                url(BuildConfig.AUTH_LOGIN_URL)
                headers {
                    remove(HttpHeaders.Authorization)
                }
                setBody(user)
            }

            if (response.status.value in 200..299) {
                logIn(response)
            } else {
                isSignUpError.emit(true)
            }
        } else {
            isSignUpError.emit(true)
        }
    }

    override suspend fun signIn(email: String, password: String) {
        val user = com.miraeldev.user.User(email = email, password = password)

        val response = client.post {
            url(BuildConfig.AUTH_LOGIN_URL)
            headers {
                remove(HttpHeaders.Authorization)
            }
            setBody(user)
        }

        if (response.status.value in 200..299) {
            logIn(response)
        } else {
            isSignInError.emit(true)
        }
    }

    override fun getSignInError(): Flow<Boolean> = isSignInError.asSharedFlow()

    override fun getSignUpError(): Flow<Boolean> = isSignUpError.asSharedFlow()

    override suspend fun checkAuthState() {
        if (localService.getBearerToken().isNullOrEmpty() ||
            localService.getRefreshToken().isNullOrEmpty()
        ) {
            userDataRepository.setUserUnAuthorizedStatus()
        } else {
            userDataRepository.setUserAuthorizedStatus()
        }
    }

    override suspend fun changePassword() {
        val bearer = localService.getBearerToken()
        client.post {
            url(BuildConfig.AUTH_CHANGE_PASSWORD_URL)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearer")
            }
//            setBody()
        }
    }

    override suspend fun checkVkAuthState() {
        val currentToken = token
        val loggedIn = currentToken != null && currentToken.isValid
        if (loggedIn) userDataRepository.setUserAuthorizedStatus() else userDataRepository.setUserUnAuthorizedStatus()
    }

    override suspend fun logOutUser() {
        val bearerToken = localService.getBearerToken()
        val refreshToken = localService.getRefreshToken()
        val response = client.post {
            url(BuildConfig.AUTH_LOGOUT_URL)
            headers {
                append(HttpHeaders.Authorization, "Bearer $bearerToken")
            }
            setBody(RefreshToken(refreshToken))
        }
//        if(response.status.isSuccess()){
        userDataRepository.setUserUnAuthorizedStatus()
//        }
    }

    private suspend fun logIn(response: HttpResponse) {

        Log.d("tag", "log in")
        userDataRepository.setUserAuthorizedStatus()

        val token = response.body<Token>()

        localService.saveBearerToken(token.bearerToken)
        localService.saveRefreshToken(token.refreshToken)
    }
}