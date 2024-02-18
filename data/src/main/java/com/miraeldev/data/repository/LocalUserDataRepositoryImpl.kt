package com.miraeldev.data.repository

import android.util.Log
import com.miraeldev.LocalUserDataRepository
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.models.auth.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LocalUserDataRepositoryImpl @Inject constructor(
    private val localService: LocalTokenService,
) : LocalUserDataRepository {

    private val userAuthState = MutableStateFlow(AuthState.Initial as AuthState)
    override fun getUserStatus(): StateFlow<AuthState> {
        CoroutineScope(Dispatchers.Main.immediate).launch {
            userAuthState.collect {
                Log.d("tag","getUserStatus "+userAuthState.value.toString())
            }
        }
        return userAuthState.asStateFlow()
    }

    override suspend fun setUserUnAuthorizedStatus() {
        localService.saveBearerToken("")
        localService.saveRefreshToken("")
        Log.d("tag", "unauthorized")
        userAuthState.value = AuthState.NotAuthorized
    }

    override suspend fun setUserAuthorizedStatus() {
        userAuthState.value = AuthState.Authorized
    }


}