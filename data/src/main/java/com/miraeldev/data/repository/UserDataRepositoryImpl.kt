package com.miraeldev.data.repository

import com.miraeldev.UserDataRepository
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.data.dataStore.localUser.LocalUserStoreApi
import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.local.AppDatabase
import com.miraeldev.data.local.models.user.toDbModel
import com.miraeldev.data.local.models.user.toUserModel
import com.miraeldev.data.mapper.UserModelsMapper
import com.miraeldev.data.remote.ApiRoutes
import com.miraeldev.data.remote.dto.UserDto
import com.miraeldev.data.remote.dto.toUserDbModel
import com.miraeldev.di.AppHttpClient
import com.miraeldev.models.models.userDataModels.toLocalUserEmail
import com.miraeldev.user.User
import com.miraeldev.user.UserEmail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

@Inject
class UserDataRepositoryImpl constructor(
//    private val client: HttpClient,
    private val localTokenService: LocalTokenService,
    private val localUserManager: LocalUserStoreApi,
    private val userModelsMapper: UserModelsMapper,
    private val appDatabase: AppDatabase,
) : UserDataRepository {

    override suspend fun saveRemoteUser(): Boolean {

        val bearerToken = localTokenService.getBearerToken()

//        val getUserResponse = client.get {
//            url(ApiRoutes.GET_USER_INFO)
//            headers {
//                append(HttpHeaders.Authorization, "Bearer $bearerToken")
//            }
//        }

//        val userDbModel = getUserResponse.body<UserDto>().toUserDbModel()
//
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
        val bearerToken = localTokenService.getBearerToken()

//        val changePasswordResponse = client.post {
//            url(ApiRoutes.CHANGE_PASSWORD)
//            headers {
//                append(HttpHeaders.Authorization, "Bearer $bearerToken")
//            }
//            setBody(
//                mapOf(
//                    Pair("current_password", currentPassword),
//                    Pair("new_password", newPassword),
//                    Pair("repeated_password", repeatedPassword)
//                )
//            )
//        }

        return true
    }


    override suspend fun updateUser(localUser: UserEmail) {
        localUserManager.updateUser(userModelsMapper.mapLocalUserToDataModel(localUser))
    }

}