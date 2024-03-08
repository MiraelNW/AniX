package com.miraeldev.dataStore.userAuth

import com.miraeldev.models.auth.AuthState
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {

    fun getUserStatus(): Flow<AuthState>

    suspend fun setUserUnAuthorizedStatus()

    suspend fun setUserAuthorizedStatus()

}