package com.miraeldev.data.remote.userApiService

import com.miraeldev.user.User

interface UserApiService {

    fun getUser(): com.miraeldev.user.User

    suspend fun updateUser(user: com.miraeldev.user.User)

}