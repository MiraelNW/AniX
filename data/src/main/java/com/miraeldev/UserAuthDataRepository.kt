package com.miraeldev

import kotlinx.coroutines.flow.Flow

interface UserAuthDataRepository {

    suspend fun signUp(
        name: String = "",
        email: String,
        password: String,
        imagePath: String = ""
    )

    suspend fun signIn(email: String, password: String)

    fun getSignInError(): Flow<Boolean>

    fun getSignUpError(): Flow<Boolean>

    suspend fun checkAuthState()

    suspend fun changePassword()

    suspend fun checkVkAuthState()

    suspend fun logOutUser()

}