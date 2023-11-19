package com.miraeldev

import kotlinx.coroutines.flow.Flow

interface ForgotPasswordDataRepository {


    fun getSignUpError(): Flow<Boolean>

    suspend fun saveNewPassword(email: String, newPassword: String)

    suspend fun sendNewOtp()

    suspend fun verifyOtp(otp: String): Boolean

    suspend fun checkEmailExist(email: String): Boolean

}