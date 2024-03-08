package com.miraelDev.vauma.glue.account.repositories

import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.account.data.AccountRepository
import com.miraeldev.dataStore.PreferenceClient
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class AccountRepositoryImpl(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository,
    private val preferenceClient: PreferenceClient
) : AccountRepository {

    override suspend fun logOutUser(): Boolean {
        return userAuthDataRepository.logOutUser()
    }

    override suspend fun getUserInfo(): User {
        return userDataRepository.getUserInfo()
    }

    override suspend fun getUserEmail(): String {
        return userDataRepository.getUserEmail().email
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        repeatedPassword: String
    ) :Boolean{
       return userDataRepository.changePassword(currentPassword, newPassword, repeatedPassword)
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        preferenceClient.setDarkTheme(isDarkTheme)
    }

    override suspend fun setIsWifiOnly(isWifi: Boolean) {
        preferenceClient.setIsWifiOnly(isWifi)
    }

    override suspend fun getIsWifiOnly(): Flow<Boolean> {
        return preferenceClient.getIsWifiOnly()
    }
}