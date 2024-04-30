package com.miraeldev.api

interface ForgotPasswordDataRepository {

    suspend fun saveNewPassword(email: String, newPassword: String): Boolean

    suspend fun sendNewOtp()

    suspend fun verifyOtp(otp: String): Boolean

    suspend fun checkEmailExist(email: String): Boolean
}