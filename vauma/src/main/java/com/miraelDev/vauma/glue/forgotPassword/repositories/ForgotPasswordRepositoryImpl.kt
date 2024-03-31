package com.miraelDev.vauma.glue.forgotPassword.repositories

import com.miraeldev.api.ForgotPasswordDataRepository
import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import me.tatarka.inject.annotations.Inject

@Inject
class ForgotPasswordRepositoryImpl(
    private val forgotPasswordRepository: ForgotPasswordDataRepository
) : ForgotPasswordRepository {

    override suspend fun saveNewPassword(email: String, newPassword: String): Boolean {
        return forgotPasswordRepository.saveNewPassword(email, newPassword)
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