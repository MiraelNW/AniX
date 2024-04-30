package com.miraeldev.signup.data

import com.miraeldev.models.user.User

interface SignUpRepository {

    suspend fun signUp(user: User): Boolean

    suspend fun updateUser(email: String)

    suspend fun verifyOtpCode(otpToken: String, user: User): Boolean
}