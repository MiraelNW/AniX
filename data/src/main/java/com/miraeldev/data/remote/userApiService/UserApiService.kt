package com.miraeldev.data.remote.userApiService

interface UserApiService {

    fun getUser(): com.miraeldev.user.User

    suspend fun updateUser(user: com.miraeldev.user.User)

}