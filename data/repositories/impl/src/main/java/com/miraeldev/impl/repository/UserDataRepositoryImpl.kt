package com.miraeldev.impl.repository

import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.LocalUserStoreApi
import com.miraeldev.api.UserDao
import com.miraeldev.api.UserDataRepository
import com.miraeldev.impl.mapper.UserModelsMapper
import com.miraeldev.impl.models.userDataModels.toLocalUserEmail
import com.miraeldev.models.anime.LastWatchedAnime
import com.miraeldev.models.dto.UserDto
import com.miraeldev.models.dto.toModel
import com.miraeldev.models.user.User
import com.miraeldev.user.UserEmail
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

@Inject
class UserDataRepositoryImpl(
    private val appNetworkClient: AppNetworkClient,
    private val localUserManager: LocalUserStoreApi,
    private val userModelsMapper: UserModelsMapper,
    private val userDao: UserDao,
) : UserDataRepository {
    override suspend fun saveRemoteUser(): Boolean {
        val getUserResponse = appNetworkClient.saveRemoteUser()

        if (getUserResponse.status.isSuccess()) {
            val userDbModel = getUserResponse.body<UserDto>().toModel()
            userDao.insertUser(userDbModel)
        }

        return getUserResponse.status.isSuccess()
    }

    override suspend fun saveLastWatchedAnime(lastWatchedAnime: LastWatchedAnime) {
        var user = userDao.getUserFlow().first()

        user = user.copy(lastWatchedAnime = lastWatchedAnime)

        userDao.insertUser(user)
    }

    override suspend fun getUserInfo(): User {
        return userDao.getUser() ?: User()
    }

    override suspend fun getUserEmail(): UserEmail {
        return localUserManager.getUserEmail().toLocalUserEmail()
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): Boolean {
        val changePasswordResponse =
            appNetworkClient.changePassword(currentPassword, newPassword, repeatedPassword)

        return changePasswordResponse.status.isSuccess()
    }

    override suspend fun updateUser(localUser: UserEmail) {
        localUserManager.updateUser(userModelsMapper.mapLocalUserToDataModel(localUser))
    }
}