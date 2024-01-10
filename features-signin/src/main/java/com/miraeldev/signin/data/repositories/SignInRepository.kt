package com.miraeldev.signin.data.repositories

import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(username: String, password: String)

    fun getSignInError(): Flow<Boolean>

    suspend fun loginWithVk(accessToken:String, userId:String,email:String?)

    suspend fun getUserEmail(): String

    suspend fun logInWithGoogle(idToken:String)
}