package com.miraelDev.vauma.data.remote.userApiService

import com.miraelDev.vauma.data.dataStore.LocalTokenService
import com.miraelDev.vauma.data.remote.ApiResult
import com.miraelDev.vauma.data.remote.ApiRoutes
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.data.remote.NetworkHandler
import com.miraelDev.vauma.data.remote.dto.AnimeInfoDto
import com.miraelDev.vauma.domain.models.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import okhttp3.internal.immutableListOf
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