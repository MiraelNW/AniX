package com.miraeldev

import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface UserAuthDataRepository {

    suspend fun signUp(user: User)

    suspend fun verifyOtpCode(user: User,otpToken:String)

    suspend fun signIn(username: String, password: String)

    suspend fun logInWithGoogle(idToken:String)

    fun getSignInError(): Flow<Boolean>

    fun getSignUpError(): Flow<Boolean>

    fun getRegistrationCompleteResult(): Flow<Boolean>

    suspend fun checkAuthState()

    suspend fun changePassword()

    suspend fun loginWithVk(accessToken:String,userId:String,email:String?)

    suspend fun logOutUser()

}