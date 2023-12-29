package com.miraeldev

import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.user.User
import com.miraeldev.user.UserEmail
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    suspend fun saveRemoteUser(): Boolean

    suspend fun saveLastWatchedAnime(lastWatchedAnime: LastWatchedAnime)

    fun getUserInfo(): Flow<User>

    suspend fun getUserEmail(): UserEmail

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ):Boolean

    suspend fun updateUser(localUser: UserEmail)

}