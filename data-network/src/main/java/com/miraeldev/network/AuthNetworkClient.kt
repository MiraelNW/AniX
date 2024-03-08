package com.miraeldev.network

import com.miraeldev.user.User
import io.ktor.client.statement.HttpResponse


interface AuthNetworkClient {
    suspend fun saveNewPassword(email: String, newPassword: String): HttpResponse
    suspend fun verifyOtpForgotPassword(otp: String): HttpResponse
    suspend fun checkEmailExist(email: String): HttpResponse
    suspend fun signUp(user: User): HttpResponse
    suspend fun verifyOtpCode(user: User, otpToken: String): HttpResponse
    suspend fun signIn(email: String, password: String): HttpResponse
    suspend fun logInWithGoogle(idToken: String): HttpResponse
    suspend fun loginWithVk(accessToken: String, userId: String, email: String?): HttpResponse
    suspend fun logOutUser(refreshToken: String): HttpResponse
}