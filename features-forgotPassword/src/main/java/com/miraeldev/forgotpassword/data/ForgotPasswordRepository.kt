package com.miraeldev.forgotpassword.data

import kotlinx.coroutines.flow.Flow

interface ForgotPasswordRepository {

    fun getSignUpError(): Flow<Boolean>

    suspend fun saveNewPassword(email: String, newPassword: String): Boolean

    suspend fun sendNewOtp()

    suspend fun verifyOtp(otp: String): Boolean

    suspend fun checkEmailExist(email: String): Boolean

}