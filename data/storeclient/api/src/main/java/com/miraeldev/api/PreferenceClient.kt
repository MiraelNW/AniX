package com.miraeldev.api

import kotlinx.coroutines.flow.Flow

interface PreferenceClient {

    suspend fun setIsWifiOnly(isWifi: Boolean)
    suspend fun getIsWifiOnly(): Flow<Boolean>

    suspend fun saveBearerToken(bearerToken: String)
    suspend fun getBearerToken(): String
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun getRefreshToken(): String

    suspend fun getDarkTheme(): Flow<Boolean>
    suspend fun setDarkTheme(isDarkTheme: Boolean)
}