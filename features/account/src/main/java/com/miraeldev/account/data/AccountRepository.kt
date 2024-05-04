package com.miraeldev.account.data

import com.miraeldev.models.user.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun logOutUser(): Boolean

    suspend fun getUserInfo(): User

    suspend fun getUserEmail(): String

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): Boolean

    suspend fun setDarkTheme(isDarkTheme: Boolean)

    suspend fun setIsWifiOnly(isWifi: Boolean)

    suspend fun getIsWifiOnly(): Flow<Boolean>
}