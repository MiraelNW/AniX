package com.miraelDev.vauma.domain.repository

import com.miraeldev.user.UserEmail
import com.miraeldev.auth.AuthState
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun checkAuthState()

    suspend fun getDarkTheme(): Boolean

    fun getUserStatus(): Flow<AuthState>

    suspend fun getLocalUser(): UserEmail

    suspend fun setDarkTheme(isDarkTheme: Boolean)

}