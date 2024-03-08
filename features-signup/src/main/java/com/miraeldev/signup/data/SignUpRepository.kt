package com.miraeldev.signup.data

import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    suspend fun signUp(user: User): Boolean

    suspend fun updateUser(email: String)

    suspend fun verifyOtpCode(otpToken: String, user: User): Boolean

}