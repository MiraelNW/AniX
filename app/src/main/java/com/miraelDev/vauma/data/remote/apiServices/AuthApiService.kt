package com.miraelDev.vauma.data.remote.apiServices

import com.miraelDev.vauma.data.remote.ApiRoutes
import com.miraelDev.vauma.domain.models.Token
import com.miraelDev.vauma.domain.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import javax.inject.Inject

class AuthApiService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun registrationUser(user: User): Token =
        httpClient.post {
            url(ApiRoutes.REGISTRATION)
            setBody(user)
        }.body()

}