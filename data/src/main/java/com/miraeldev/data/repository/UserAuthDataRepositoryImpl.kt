package com.miraeldev.data.repository

import com.miraeldev.LocalUserDataRepository
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.network.AuthNetworkClient
import com.miraeldev.extensions.sendRequest
import com.miraeldev.models.models.auth.Token
import com.miraeldev.user.User
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.tatarka.inject.annotations.Inject


@Inject
class UserAuthDataRepositoryImpl(
    private val localService: LocalTokenService,
    private val localUserDataRepository: LocalUserDataRepository,
    private val userDataRepository: UserDataRepository,
    private val appDatabase: AppDatabase,
    private val authNetworkClient: AuthNetworkClient
) : UserAuthDataRepository {

    private val isSignInError = MutableSharedFlow<Boolean>()
    private val isSignUpError = MutableSharedFlow<Boolean>()

    override fun getSignInError(): Flow<Boolean> = isSignInError.asSharedFlow()

    override fun getSignUpError(): Flow<Boolean> = isSignUpError.asSharedFlow()

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
        if (localService.getBearerToken().isNullOrEmpty() ||
            localService.getRefreshToken().isEmpty()
        ) {
            localUserDataRepository.setUserUnAuthorizedStatus()
        } else {
            localUserDataRepository.setUserAuthorizedStatus()
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
            val refreshToken = localService.getRefreshToken()
            val response = authNetworkClient.logOutUser(refreshToken)

            if (response.status.isSuccess()) {
                localUserDataRepository.setUserUnAuthorizedStatus()
                appDatabase.userDao().deleteOldUser()
            }
            response.status.isSuccess()
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
}