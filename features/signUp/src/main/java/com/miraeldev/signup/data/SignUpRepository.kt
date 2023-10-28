package com.miraeldev.signup.data

import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    suspend fun signUp(email: String, password: String, name: String = "", imagePath: String = "")

    suspend fun updateUser(email: String)

    fun getSignUpError(): Flow<Boolean>

}