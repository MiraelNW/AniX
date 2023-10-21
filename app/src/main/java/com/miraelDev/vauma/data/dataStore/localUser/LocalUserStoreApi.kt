package com.miraelDev.vauma.data.dataStore.localUser

import com.miraelDev.vauma.domain.models.user.LocalUser

interface LocalUserStoreApi {
    suspend fun updateUser(localUser: LocalUser)
    suspend fun getUser():LocalUser
}