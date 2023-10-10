package com.miraelDev.vauma.domain.repository

import com.miraelDev.vauma.domain.models.auth.AuthState
import com.miraelDev.vauma.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(): User

    fun getUserStatus(): Flow<AuthState>

    suspend fun setUserUnAuthorizedStatus()

    suspend fun setUserAuthorizedStatus()

    suspend fun updateUser(user: User)

}