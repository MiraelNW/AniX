package com.miraelDev.vauma.data.dataStore

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class LocalTokenService @Inject constructor(private val preferenceManager: PreferenceManager) {

    suspend fun saveBearerToken(bearerToken: String) {
        preferenceManager.putPreference(bearerTokenKey, bearerToken)
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        preferenceManager.putPreference(refreshTokenKey, refreshToken)
    }

    suspend fun getBearerToken(): String? {
        return preferenceManager.getPreference(bearerTokenKey, "").firstOrNull()
    }

    suspend fun getRefreshToken(): String {
        return preferenceManager.getPreference(refreshTokenKey, "").first()
    }

    private val bearerTokenKey = stringPreferencesKey("bearer_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
}