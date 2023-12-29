package com.miraeldev

import com.miraeldev.auth.AuthState
import com.miraeldev.user.User
import com.miraeldev.user.UserEmail
import kotlinx.coroutines.flow.Flow

interface LocalUserDataRepository {

    fun getUserStatus(): Flow<AuthState>

    suspend fun setUserUnAuthorizedStatus()

    suspend fun setUserAuthorizedStatus()

}