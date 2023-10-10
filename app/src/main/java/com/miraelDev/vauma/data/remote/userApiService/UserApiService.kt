package com.miraelDev.vauma.data.remote.userApiService

import com.miraelDev.vauma.data.remote.ApiResult
import com.miraelDev.vauma.domain.models.user.User

interface UserApiService {

    fun getUser():User

    suspend fun updateUser(user: User)

}