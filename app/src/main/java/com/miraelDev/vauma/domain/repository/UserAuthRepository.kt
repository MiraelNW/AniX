package com.miraelDev.vauma.domain.repository

import com.miraelDev.vauma.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {

    suspend fun signUp(user: User)

    suspend fun signIn(user: User)

    fun getSignInError():Flow<Boolean>

    fun getSignUpError():Flow<Boolean>

    suspend fun checkAuthState()

    suspend fun changePassword()

    suspend fun checkVkAuthState()

    suspend fun logOutUser()

}