package com.miraeldev.api

import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.models.user.User
import com.miraeldev.user.UserEmail

interface UserDataRepository {

    suspend fun saveRemoteUser(): Boolean

    suspend fun saveLastWatchedAnime(lastWatchedAnime: LastWatchedAnime)

    suspend fun getUserInfo(): User

    suspend fun getUserEmail(): UserEmail

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ):Boolean

    suspend fun updateUser(localUser: UserEmail)

}