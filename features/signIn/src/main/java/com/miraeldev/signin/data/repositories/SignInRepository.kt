package com.miraeldev.signin.data.repositories

import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(email: String, password: String)

    fun getSignInError(): Flow<Boolean>

    suspend fun checkVkAuthState()

    suspend fun getUserEmail(): String
}