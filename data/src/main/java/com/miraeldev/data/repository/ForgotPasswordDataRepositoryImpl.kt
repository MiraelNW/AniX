package com.miraeldev.data.repository

import com.miraeldev.ForgotPasswordDataRepository
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.data.BuildConfig
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.network.AuthNetworkClient
import com.miraeldev.di.AuthHttpClient
import com.miraeldev.extensions.sendRequest
import com.miraeldev.models.models.auth.Token
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.tatarka.inject.annotations.Inject


@Inject
class ForgotPasswordDataRepositoryImpl constructor(
    private val localService: LocalTokenService,
    private val userDataRepository: LocalUserDataRepository,
) : ForgotPasswordDataRepository {

    private val client: AuthHttpClient = AuthNetworkClient.createClient()
    override fun getSignUpError(): Flow<Boolean> {
        return emptyFlow()
    }

    override suspend fun saveNewPassword(email: String, newPassword: String):Boolean {
        return sendRequest {
            val saveNewPasswordResponse = client.post {
                url(BuildConfig.CREATE_NEW_PASSWORD)
                setBody(
                    mapOf(
                        Pair("email", email),
                        Pair("password", newPassword),
                    )
                )
            }

            if (saveNewPasswordResponse.status.isSuccess()) {
                logIn(saveNewPasswordResponse)
            }
            saveNewPasswordResponse.status.isSuccess()
        }
    }

    override suspend fun sendNewOtp() {

    }

    override suspend fun verifyOtp(otp: String): Boolean {
        return sendRequest {
            val verifyOtpResponse = client.post {
                url(BuildConfig.VERIFY_OTP_FORGOT_PASSWORD)
                setBody(
                    mapOf(
                        Pair("token", otp)
                    )
                )
            }

            verifyOtpResponse.status.isSuccess()
        }
    }

    override suspend fun checkEmailExist(email: String): Boolean {
        return sendRequest {
            val checkEmailExistResponse = client.post {
                url(BuildConfig.CHECK_EMAIL)
                setBody(
                    mapOf(
                        Pair("email", email)
                    )
                )
            }
            checkEmailExistResponse.status.isSuccess()
        }
    }

    private suspend fun logIn(response: HttpResponse) {

        val token = response.body<Token>()

        localService.saveBearerToken(token.bearerToken)
        localService.saveRefreshToken(token.refreshToken)

        userDataRepository.setUserAuthorizedStatus()
    }


}