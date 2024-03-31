package com.miraeldev.api

import kotlinx.coroutines.flow.Flow

interface ForgotPasswordDataRepository {

    suspend fun saveNewPassword(email: String, newPassword: String): Boolean

    suspend fun sendNewOtp()

    suspend fun verifyOtp(otp: String): Boolean

    suspend fun checkEmailExist(email: String): Boolean

}