package com.miraelDev.vauma.glue.forgotPassword.repositories

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import com.miraeldev.signin.data.repositories.SignInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ForgotPasswordRepositoryImpl @Inject constructor(
) : ForgotPasswordRepository {

    override fun getSignUpError(): Flow<Boolean> {
//        TODO("Not yet implemented")
        return emptyFlow()
    }

    override suspend fun saveNewPassword(newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendNewOtp() {

    }

    override suspend fun verifyOtp(otp: String): Boolean {
        return true
    }


}