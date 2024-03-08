package com.miraeldev.dataStore.userAuth

import com.miraeldev.dataStore.PreferenceClient
import com.miraeldev.models.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject

@Inject
class UserAuthRepositoryImpl(
    private val preferenceClient: PreferenceClient,
) : UserAuthRepository {

    private val userAuthState = MutableStateFlow(AuthState.Initial as AuthState)
    override fun getUserStatus(): StateFlow<AuthState> {
        return userAuthState.asStateFlow()
    }

    override suspend fun setUserUnAuthorizedStatus() {
        preferenceClient.saveBearerToken("")
        preferenceClient.saveRefreshToken("")
        userAuthState.value = AuthState.NotAuthorized
    }

    override suspend fun setUserAuthorizedStatus() {
        userAuthState.value = AuthState.Authorized
    }
}