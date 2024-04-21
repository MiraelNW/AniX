package com.miraeldev.api

import com.miraeldev.models.user.User

interface UserAuthDataRepository {

    suspend fun signUp(user: User): Boolean

    suspend fun verifyOtpCode(user: User, otpToken: String): Boolean

    suspend fun signIn(email: String, password: String): Boolean

    suspend fun logInWithGoogle(idToken: String)

    suspend fun checkAuthState()

    suspend fun loginWithVk(accessToken: String, userId: String, email: String?)

    suspend fun logOutUser(): Boolean

}