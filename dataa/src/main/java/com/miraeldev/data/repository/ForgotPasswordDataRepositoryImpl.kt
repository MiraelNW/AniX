package com.miraeldev.data.repository

import com.miraeldev.ForgotPasswordDataRepository
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.AuthNetworkClient
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.extensions.sendRequest
import com.miraeldev.models.models.auth.Token
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import me.tatarka.inject.annotations.Inject


@Inject
class ForgotPasswordDataRepositoryImpl(
    private val authNetworkClient: com.miraeldev.api.AuthNetworkClient,
    private val preferenceClient: PreferenceClient,
    private val userDataRepository: UserAuthRepository,
) : ForgotPasswordDataRepository {

    override suspend fun saveNewPassword(email: String, newPassword: String): Boolean {
        return sendRequest {
            val saveNewPasswordResponse = authNetworkClient.saveNewPassword(email, newPassword)

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
            val verifyOtpResponse = authNetworkClient.verifyOtpForgotPassword(otp)
            verifyOtpResponse.status.isSuccess()
        }
    }

    override suspend fun checkEmailExist(email: String): Boolean {
        return sendRequest {
            val checkEmailExistResponse = authNetworkClient.checkEmailExist(email)
            checkEmailExistResponse.status.isSuccess()
        }
    }

    private suspend fun logIn(response: HttpResponse) {

        val token = response.body<Token>()

        preferenceClient.saveBearerToken(token.bearerToken)
        preferenceClient.saveRefreshToken(token.refreshToken)

        userDataRepository.setUserAuthorizedStatus()
    }


}