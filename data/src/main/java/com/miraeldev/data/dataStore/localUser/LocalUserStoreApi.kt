package com.miraeldev.data.dataStore.localUser

import com.miraeldev.domain.models.userDataModels.LocalUserDataModel
import com.miraeldev.user.LocalUser

interface LocalUserStoreApi {
    suspend fun updateUser(localUser: LocalUserDataModel)
    suspend fun getUser(): LocalUserDataModel
}