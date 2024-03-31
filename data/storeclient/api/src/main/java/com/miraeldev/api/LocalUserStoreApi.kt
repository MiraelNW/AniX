package com.miraeldev.api

import com.miraeldev.api.models.LocalUserEmailDataModel

interface LocalUserStoreApi {
    suspend fun updateUser(localUser: LocalUserEmailDataModel)
    suspend fun getUserEmail(): LocalUserEmailDataModel
}