package com.miraeldev.data.remote.userApiService

import com.miraeldev.data.dataStore.tokenService.LocalTokenService
import com.miraeldev.data.remote.NetworkHandler
import com.miraeldev.user.User
import io.ktor.client.HttpClient
import javax.inject.Inject

internal class UserApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val networkHandler: NetworkHandler,
    private val localTokenService: LocalTokenService
) : UserApiService {
    override fun getUser(): com.miraeldev.user.User {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: com.miraeldev.user.User) {
//        TODO("Not yet implemented")
    }


}