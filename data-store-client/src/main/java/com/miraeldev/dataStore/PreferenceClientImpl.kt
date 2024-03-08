package com.miraeldev.dataStore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import java.io.IOException


private val Context.dataStore by preferencesDataStore(name = "preference")

@Inject
class PreferenceClientImpl(context: Context) : PreferenceClient {

    private val dataStore = context.dataStore
    private fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T):
            Flow<T> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val result = preferences[key] ?: defaultValue
        result
    }

    private suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun setIsWifiOnly(isWifi: Boolean) {
        putPreference(PreferencesConstants.isWifiOnlyKey, isWifi)
    }

    override suspend fun getIsWifiOnly(): Flow<Boolean> {
        return getPreference(PreferencesConstants.isWifiOnlyKey, true)
    }

    override suspend fun saveBearerToken(bearerToken: String) {
        putPreference(PreferencesConstants.bearerTokenKey, bearerToken)
    }

    override suspend fun getBearerToken(): String {
        return getPreference(PreferencesConstants.bearerTokenKey, "").first()
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        putPreference(PreferencesConstants.refreshTokenKey, refreshToken)
    }

    override suspend fun getRefreshToken(): String {
        return getPreference(PreferencesConstants.refreshTokenKey, "")
            .first()
    }

    override suspend fun getDarkTheme(): Flow<Boolean> {
        return getPreference(PreferencesConstants.darkTheme, false)
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        putPreference(PreferencesConstants.darkTheme, isDarkTheme)
    }

}