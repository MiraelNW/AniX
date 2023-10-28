package com.miraeldev

import com.miraeldev.auth.AuthState
import com.miraeldev.user.LocalUser
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun getRemoteUser(): User

    suspend fun getLocalUser(): LocalUser

    fun getUserStatus(): Flow<AuthState>

    suspend fun setUserUnAuthorizedStatus()

    suspend fun setUserAuthorizedStatus()

    suspend fun updateUser(localUser: LocalUser)

}