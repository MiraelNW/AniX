package com.miraeldev.data.repository

import com.miraeldev.UserDataRepository
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.local.models.user.toDbModel
import com.miraeldev.data.local.models.user.toUserModel
import com.miraeldev.data.mapper.UserModelsMapper
import com.miraeldev.data.remote.dto.UserDto
import com.miraeldev.data.remote.dto.toUserDbModel
import com.miraeldev.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.models.models.userDataModels.toLocalUserEmail
import com.miraeldev.network.AppNetworkClient
import com.miraeldev.network.impl.AppNetworkClientImpl
import com.miraeldev.user.User
import com.miraeldev.user.UserEmail
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

@Inject
class UserDataRepositoryImpl(
//    private val appNetworkClient: AppNetworkClient,
    private val localUserManager: LocalUserStoreApi,
    private val userModelsMapper: UserModelsMapper,
    private val appDatabase: AppDatabase,
) : UserDataRepository {
    override suspend fun saveRemoteUser(): Boolean {
//        val getUserResponse = appNetworkClient.saveRemoteUser()

//        val userDbModel = getUserResponse.body<UserDto>().toUserDbModel()

//        appDatabase.userDao().insertUser(userDbModel)

        return true
    }

    override suspend fun saveLastWatchedAnime(lastWatchedAnime: LastWatchedAnime) {
        var user = appDatabase.userDao().getUserFlow().first()

        user = user.copy(lastWatchedAnimeDbModel = lastWatchedAnime.toDbModel())

        appDatabase.userDao().insertUser(user)
    }

    override suspend fun getUserInfo(): User {
        return appDatabase.userDao().getUser()?.toUserModel() ?: User()
    }

    override suspend fun getUserEmail(): UserEmail {
        return localUserManager.getUserEmail().toLocalUserEmail()
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): Boolean {
//        val changePasswordResponse =
//            appNetworkClient.changePassword(currentPassword, newPassword, repeatedPassword)

        return true
    }

    override suspend fun updateUser(localUser: UserEmail) {
        localUserManager.updateUser(userModelsMapper.mapLocalUserToDataModel(localUser))
    }

}