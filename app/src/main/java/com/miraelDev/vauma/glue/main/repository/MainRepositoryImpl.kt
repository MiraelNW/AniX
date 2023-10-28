package com.miraelDev.vauma.glue.main.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.PreferenceDataStoreAPI
import com.miraeldev.user.LocalUser
import com.miraeldev.auth.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val DARK_THEME_KEY = "dark theme key"

class MainRepositoryImpl @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userAuthDataRepository: UserAuthDataRepository,
    private val preferenceDataStoreAPI: PreferenceDataStoreAPI
) : MainRepository {
    override suspend fun checkAuthState() {
        userAuthDataRepository.checkAuthState()
    }

    override suspend fun getDarkTheme(): Boolean {
        return preferenceDataStoreAPI
            .getPreference(booleanPreferencesKey(DARK_THEME_KEY), false)
            .first()
    }

    override fun getUserStatus(): Flow<AuthState> {
        return userDataRepository.getUserStatus()
    }

    override suspend fun getLocalUser(): LocalUser {
        return userDataRepository.getLocalUser()
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        preferenceDataStoreAPI.putPreference(booleanPreferencesKey(DARK_THEME_KEY), isDarkTheme)
    }
}