package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.data.dataStore.LocalTokenService
import com.miraelDev.vauma.domain.models.auth.AuthState
import com.miraelDev.vauma.domain.models.auth.Token
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.repository.UserAuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val localService: LocalTokenService,
) : UserAuthRepository {

    private val userAuthState = MutableStateFlow(AuthState.Initial as AuthState)

    override suspend fun signUp(user: User) {
        client.post {
            url("auth/register/")
            setBody(user)
        }
        delay(1000)

        val response = client.post {
            url("auth/token/")
            setBody(user)
        }

        if (response.status.value in 200..299) {

            userAuthState.value = AuthState.Authorized

            val token = response.body<Token>()

            localService.saveBearerToken(token.bearerToken)
            localService.saveRefreshToken(token.refreshToken)
        }


    }

    override suspend fun signIn(user: User) {
        val response = client.post {
            url("auth/token/")
            setBody(user)
        }

        if (response.status.value in 200..299) {

            userAuthState.value = AuthState.Authorized

            val token = response.body<Token>()

            localService.saveBearerToken(token.bearerToken)
            localService.saveRefreshToken(token.refreshToken)
        }
    }

    override suspend fun checkAuthState() {
        if (localService.getBearerToken().isNullOrEmpty() ||
            localService.getRefreshToken().isNullOrEmpty()
        ) {
            userAuthState.value = AuthState.NotAuthorized
        } else {
            userAuthState.value = AuthState.Authorized
        }
    }

    override fun getUserAuthStatus(): Flow<AuthState> = userAuthState.asStateFlow()
}