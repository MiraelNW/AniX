package com.miraelDev.vauma.glue.account.repositories

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.miraeldev.PreferenceDataStoreAPI
import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.account.data.AccountRepository
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository,
    private val preferenceDataStoreAPI: PreferenceDataStoreAPI
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

    override suspend fun setPreference(key: String, value: Boolean) {
        preferenceDataStoreAPI.putPreference(booleanPreferencesKey(key), value)
    }

    override suspend fun getPreference(key: String): Boolean {
        return preferenceDataStoreAPI.getPreference(booleanPreferencesKey(key), true).first()
    }


}