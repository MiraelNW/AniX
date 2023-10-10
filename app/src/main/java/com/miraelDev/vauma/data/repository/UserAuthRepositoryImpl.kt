package com.miraelDev.vauma.data.repository

import android.util.Log
import com.miraelDev.vauma.data.dataStore.LocalTokenService
import com.miraelDev.vauma.domain.models.auth.AuthState
import com.miraelDev.vauma.domain.models.auth.Token
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.repository.UserAuthRepository
import com.miraelDev.vauma.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val localService: LocalTokenService,
    private val userRepository: UserRepository
) : UserAuthRepository {


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

            userRepository.setUserAuthorizedStatus()

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

            userRepository.setUserAuthorizedStatus()

            val token = response.body<Token>()

            localService.saveBearerToken(token.bearerToken)
            localService.saveRefreshToken(token.refreshToken)
        }
    }

    override suspend fun checkAuthState() {
        if (localService.getBearerToken().isNullOrEmpty() ||
            localService.getRefreshToken().isNullOrEmpty()
        ) {
           userRepository.setUserUnAuthorizedStatus()
        } else {
            userRepository.setUserAuthorizedStatus()
        }
    }

    override suspend fun logOutUser() {
        userRepository.setUserUnAuthorizedStatus()
    }
}