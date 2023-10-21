package com.miraelDev.vauma.data.remote.userApiService

import com.miraelDev.vauma.data.dataStore.tokenService.LocalTokenService
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.domain.models.user.User
import io.ktor.client.HttpClient
import javax.inject.Inject

class UserApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val networkHandler: NetworkHandler,
    private val localTokenService: LocalTokenService
) : UserApiService {
    override fun getUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
//        TODO("Not yet implemented")
    }


}