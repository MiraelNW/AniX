package com.miraelDev.vauma.glue.main.repository

import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraeldev.api.PreferenceClient
import com.miraeldev.api.UserAuthDataRepository
import com.miraeldev.api.UserAuthRepository
import com.miraeldev.api.UserDataRepository
import com.miraeldev.models.auth.AuthState
import com.miraeldev.models.di.scope.Singleton
import com.miraeldev.user.UserEmail
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject @Singleton
class MainRepositoryImpl(
    private val userDataRepository: UserDataRepository,
    private val userAuthDataRepository: UserAuthDataRepository,
    private val localUserAuthDataRepository: UserAuthRepository,
    private val preferenceClient: PreferenceClient
) : MainRepository {
    override suspend fun checkAuthState() {
        userAuthDataRepository.checkAuthState()
    }

    override suspend fun getDarkTheme(): Flow<Boolean> {
        return preferenceClient.getDarkTheme()
    }

    override fun getUserStatus(): Flow<AuthState> {
        return localUserAuthDataRepository.getUserStatus()
    }

    override suspend fun getLocalUser(): UserEmail {
        return userDataRepository.getUserEmail()
    }
}