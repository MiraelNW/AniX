package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.data.remote.apiServices.AuthApiService
import com.miraelDev.vauma.domain.models.User
import com.miraelDev.vauma.domain.repository.RegistrationUserRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import javax.inject.Inject

class RegistrationUserRepositoryImpl @Inject constructor(
    private val client: HttpClient,
    private val authApiService : AuthApiService
) : RegistrationUserRepository {

    override suspend fun registrationUser(user: User) {
//        client.post<>()
    }

}