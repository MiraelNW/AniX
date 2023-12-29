package com.miraeldev.data.dataStore.localUser

import com.miraeldev.domain.models.userDataModels.LocalUserEmailDataModel

interface LocalUserStoreApi {
    suspend fun updateUser(localUser: LocalUserEmailDataModel)
    suspend fun getUserEmail(): LocalUserEmailDataModel
}