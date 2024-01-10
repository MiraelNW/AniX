package com.miraeldev

import com.miraeldev.models.auth.AuthState
import kotlinx.coroutines.flow.Flow

interface LocalUserDataRepository {

    fun getUserStatus(): Flow<AuthState>

    suspend fun setUserUnAuthorizedStatus()

    suspend fun setUserAuthorizedStatus()

}