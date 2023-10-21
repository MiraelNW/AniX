package com.miraelDev.vauma.data.dataStore.preference

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceDataStoreAPI {
    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> putPreference(key: Preferences.Key<T>, value:T)
}