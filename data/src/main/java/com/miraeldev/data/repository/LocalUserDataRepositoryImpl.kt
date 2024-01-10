package com.miraeldev.data.repository

import com.miraeldev.LocalUserDataRepository
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.models.auth.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class LocalUserDataRepositoryImpl @Inject constructor(
    private val localService: LocalTokenService,
) : LocalUserDataRepository {

    private val userAuthState = MutableStateFlow(AuthState.Initial as AuthState)
    override fun getUserStatus(): Flow<AuthState> {
        return userAuthState.asStateFlow()
    }

    override suspend fun setUserUnAuthorizedStatus() {
        localService.saveBearerToken("")
        localService.saveRefreshToken("")
        userAuthState.value = AuthState.NotAuthorized
    }

    override suspend fun setUserAuthorizedStatus() {
        userAuthState.value = AuthState.Authorized
    }


}