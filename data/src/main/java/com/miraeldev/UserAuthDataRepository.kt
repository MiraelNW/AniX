package com.miraeldev

import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface UserAuthDataRepository {

    suspend fun signUp(user: User): Boolean

    suspend fun verifyOtpCode(user: User, otpToken: String): Boolean

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun logInWithGoogle(idToken: String)

    fun getSignInError(): Flow<Boolean>

    fun getSignUpError(): Flow<Boolean>

    suspend fun checkAuthState()

    suspend fun loginWithVk(accessToken: String, userId: String, email: String?)

    suspend fun logOutUser(): Boolean

}