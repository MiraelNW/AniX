package com.miraeldev.forgotpassword.data

import kotlinx.coroutines.flow.Flow

interface ForgotPasswordRepository {

    fun getSignUpError(): Flow<Boolean>

    suspend fun saveNewPassword(newPassword: String)

    suspend fun sendNewOtp()

    suspend fun verifyOtp(otp:String):Boolean

}