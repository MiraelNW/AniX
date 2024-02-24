package com.miraeldev.data.dataStore.tokenService

import androidx.datastore.preferences.core.stringPreferencesKey
import com.miraeldev.PreferenceDataStoreAPI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import me.tatarka.inject.annotations.Inject

@Inject
class LocalTokenService internal constructor(
    private val preferenceManager: PreferenceDataStoreAPI
) {

    suspend fun saveBearerToken(bearerToken: String) {
        preferenceManager.putPreference(bearerTokenKey, bearerToken)
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        preferenceManager.putPreference(refreshTokenKey, refreshToken)
    }

    suspend fun getBearerToken(): String? {
        return preferenceManager.getPreference(bearerTokenKey, "").firstOrNull()
    }

//    fun getBearerTokenFlow(): Flow<String> {
//        return preferenceManager.getPreference(bearerTokenKey, "")
//    }

    suspend fun getRefreshToken(): String {
        return preferenceManager.getPreference(refreshTokenKey, "").first()
    }

    private val bearerTokenKey = stringPreferencesKey("bearer_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
}