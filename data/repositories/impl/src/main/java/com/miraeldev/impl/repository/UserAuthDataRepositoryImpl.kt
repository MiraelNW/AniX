package com.miraeldev.impl.repository

import com.miraeldev.api.AuthNetworkClient
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.UserAuthDataRepository
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.api.UserDao
import com.miraeldev.api.UserDataRepository
import com.miraeldev.extensions.sendRequest
import com.miraeldev.impl.models.auth.Token
import com.miraeldev.models.user.User
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import me.tatarka.inject.annotations.Inject


@Inject
class UserAuthDataRepositoryImpl(
    private val preferenceClient: PreferenceClient,
    private val userAuthRepository: UserAuthRepository,
    private val userDataRepository: UserDataRepository,
    private val userDao: UserDao,
    private val authNetworkClient: AuthNetworkClient
) : UserAuthDataRepository {

    override suspend fun signUp(user: User): Boolean {
        return sendRequest {
            val signUpResponse = authNetworkClient.signUp(user)
            signUpResponse.status.isSuccess()
        }
    }

    override suspend fun verifyOtpCode(user: User, otpToken: String): Boolean {

        val verifyOtpResponse = authNetworkClient.verifyOtpCode(user, otpToken)

        return if (verifyOtpResponse.status.isSuccess()) {

            val signInResponse = authNetworkClient.signIn(user.email, user.password)

            if (signInResponse.status.isSuccess()) {
                logIn(response = signInResponse)
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    override suspend fun signIn(email: String, password: String): Boolean {

        val response = authNetworkClient.signIn(email, password)

        if (response.status.isSuccess()) {
            logIn(response = response)
        }

        return response.status.isSuccess()
    }

    override suspend fun logInWithGoogle(idToken: String) {
        val logInWithGoogleResponse = authNetworkClient.logInWithGoogle(idToken)

        if (logInWithGoogleResponse.status.isSuccess()) {
            logIn(response = logInWithGoogleResponse)
        }
    }

    override suspend fun checkAuthState() {
        if (preferenceClient.getBearerToken().isEmpty() ||
            preferenceClient.getRefreshToken().isEmpty()
        ) {
            userAuthRepository.setUserUnAuthorizedStatus()
        } else {
            userAuthRepository.setUserAuthorizedStatus()
        }
    }

    override suspend fun loginWithVk(accessToken: String, userId: String, email: String?) {
        val logInWithVkResponse = authNetworkClient.loginWithVk(accessToken, userId, email)

        if (logInWithVkResponse.status.isSuccess()) {
            logIn(response = logInWithVkResponse)
        }
    }

    override suspend fun logOutUser(): Boolean {
        return sendRequest {
            val refreshToken = preferenceClient.getRefreshToken()
            val response = authNetworkClient.logOutUser(refreshToken)

            if (response.status.isSuccess()) {
                userAuthRepository.setUserUnAuthorizedStatus()
                userDao.deleteOldUser()
            }
            response.status.isSuccess()
        }
    }

    private suspend fun logIn(response: HttpResponse) {

        val token = response.body<Token>()

        preferenceClient.saveBearerToken(token.bearerToken)
        preferenceClient.saveRefreshToken(token.refreshToken)


        val isSuccess = userDataRepository.saveRemoteUser()

        if (isSuccess) {
            userAuthRepository.setUserAuthorizedStatus()
        }
    }
}