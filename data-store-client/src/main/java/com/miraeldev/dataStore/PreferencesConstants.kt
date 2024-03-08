package com.miraeldev.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesConstants {
    private const val IS_WIFI_ONLY_KEY = "is_wifi_only_key"
    private const val BEARER_TOKEN = "bearer_token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val IS_DARK_THEME = "is_dark_theme"

    val isWifiOnlyKey = booleanPreferencesKey(IS_WIFI_ONLY_KEY)
    val bearerTokenKey = stringPreferencesKey(BEARER_TOKEN)
    val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN)
    val darkTheme = booleanPreferencesKey(IS_DARK_THEME)
}