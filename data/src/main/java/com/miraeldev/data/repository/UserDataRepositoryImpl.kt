package com.miraeldev.data.repository

import com.miraeldev.UserDataRepository
import com.miraeldev.data.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.mapper.UserModelsMapper
import com.miraeldev.auth.AuthState
import com.miraeldev.domain.models.userDataModels.toLocalUser
import com.miraeldev.user.LocalUser
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class UserDataRepositoryImpl @Inject constructor(
    private val localTokenService: LocalTokenService,
    private val localUserManager: LocalUserStoreApi,
    private val userModelsMapper: UserModelsMapper
//    private val userApiService: UserApiService
) : UserDataRepository {


    private val userAuthState = MutableStateFlow(AuthState.Initial as AuthState)

    override fun getRemoteUser(): User {
        TODO()
//        return userApiService.getUser()
    }

    override suspend fun getLocalUser(): LocalUser {
        return localUserManager.getUser().toLocalUser()
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

    override suspend fun updateUser(localUser: LocalUser) {
        localUserManager.updateUser(userModelsMapper.mapLocalUserToDataModel(localUser))
//        userApiService.updateUser(user)
    }

}