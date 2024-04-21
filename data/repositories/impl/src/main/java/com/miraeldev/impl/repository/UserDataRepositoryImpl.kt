package com.miraeldev.impl.repository

import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.api.LocalUserStoreApi
import com.miraeldev.api.UserDataRepository
import com.miraeldev.data.mapper.UserModelsMapper
import com.miraeldev.local.dao.user.UserDao
import com.miraeldev.local.mapper.toDbModel
import com.miraeldev.local.mapper.toModel
import com.miraeldev.local.models.user.toDbModel
import com.miraeldev.models.dto.UserDto
import com.miraeldev.models.models.userDataModels.toLocalUserEmail
import com.miraeldev.user.User
import com.miraeldev.user.UserEmail
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

@Inject
class UserDataRepositoryImpl(
    private val appNetworkClient: com.miraeldev.api.AppNetworkClient,
    private val localUserManager: LocalUserStoreApi,
    private val userModelsMapper: UserModelsMapper,
    private val userDao: UserDao,
) : UserDataRepository {
    override suspend fun saveRemoteUser(): Boolean {
        val getUserResponse = appNetworkClient.saveRemoteUser()

        if (getUserResponse.status.isSuccess()) {
            val userDbModel = getUserResponse.body<UserDto>().toDbModel()
            userDao.insertUser(userDbModel)
        }

        return getUserResponse.status.isSuccess()
    }

    override suspend fun saveLastWatchedAnime(lastWatchedAnime: LastWatchedAnime) {
        var user = userDao.getUserFlow().first()

        user = user.copy(lastWatchedAnimeDbModel = lastWatchedAnime.toDbModel())

        userDao.insertUser(user)
    }

    override suspend fun getUserInfo(): User {
        return userDao.getUser()?.toModel() ?: User()
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