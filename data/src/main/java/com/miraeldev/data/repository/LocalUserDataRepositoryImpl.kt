package com.miraeldev.data.repository

import android.util.Log
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.auth.AuthState
import com.miraeldev.data.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.mapper.UserModelsMapper
import com.miraeldev.di.qualifiers.CommonHttpClient
import io.ktor.client.HttpClient
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