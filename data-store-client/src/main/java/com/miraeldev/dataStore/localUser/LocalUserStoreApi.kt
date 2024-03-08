package com.miraeldev.dataStore.localUser

import com.miraeldev.dataStore.models.LocalUserEmailDataModel

interface LocalUserStoreApi {
    suspend fun updateUser(localUser: LocalUserEmailDataModel)
    suspend fun getUserEmail(): LocalUserEmailDataModel
}