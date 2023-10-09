package com.miraelDev.vauma.domain.repository

import com.miraelDev.vauma.domain.models.auth.AuthState
import com.miraelDev.vauma.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {

    suspend fun signUp(user: User)

    suspend fun signIn(user: User)

    suspend fun checkAuthState()

    fun getUserAuthStatus(): Flow<AuthState>

}