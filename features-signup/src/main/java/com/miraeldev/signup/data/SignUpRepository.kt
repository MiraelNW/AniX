package com.miraeldev.signup.data

import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    suspend fun signUp(user: User)

    suspend fun updateUser(email: String)

    suspend fun verifyOtpCode(otpToken: String, user: User)

    fun getSignUpError(): Flow<Boolean>

    fun getRegistrationCompleteResult(): Flow<Boolean>

}