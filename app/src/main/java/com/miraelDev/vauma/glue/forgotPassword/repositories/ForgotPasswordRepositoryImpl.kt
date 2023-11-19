package com.miraelDev.vauma.glue.forgotPassword.repositories

import com.miraeldev.ForgotPasswordDataRepository
import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import com.miraeldev.signin.data.repositories.SignInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ForgotPasswordRepositoryImpl @Inject constructor(
    private val forgotPasswordRepository: ForgotPasswordDataRepository
) : ForgotPasswordRepository {

    override fun getSignUpError(): Flow<Boolean> {
        return forgotPasswordRepository.getSignUpError()
    }

    override suspend fun saveNewPassword(email: String, newPassword: String) {
        forgotPasswordRepository.saveNewPassword(email, newPassword)
    }

    override suspend fun sendNewOtp() {
        forgotPasswordRepository.sendNewOtp()
    }

    override suspend fun verifyOtp(otp: String): Boolean {
        return forgotPasswordRepository.verifyOtp(otp)
    }

    override suspend fun checkEmailExist(email: String): Boolean {
        return forgotPasswordRepository.checkEmailExist(email)
    }


}