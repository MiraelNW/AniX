package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.data.dataStore.LocalTokenService
import com.miraelDev.vauma.data.remote.userApiService.UserApiService
import com.miraelDev.vauma.domain.models.auth.AuthState
import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localTokenService: LocalTokenService
//    private val userApiService: UserApiService
) : UserRepository {


    private val userAuthState = MutableStateFlow(AuthState.Initial as AuthState)

    override fun getUser(): User {
        TODO()
//        return userApiService.getUser()
    }

    override fun getUserStatus(): Flow<AuthState> {
        return userAuthState.asStateFlow()
    }

    override suspend fun setUserUnAuthorizedStatus() {
        localTokenService.saveBearerToken("")
        localTokenService.saveRefreshToken("")
        userAuthState.value = AuthState.NotAuthorized
    }

    override suspend fun setUserAuthorizedStatus() {
        userAuthState.value = AuthState.Authorized
    }

    override suspend fun updateUser(user: User) {
//        userApiService.updateUser(user)
    }

}