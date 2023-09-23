package com.miraelDev.vauma.domain.repository

import com.miraelDev.vauma.domain.models.User

interface RegistrationUserRepository {

    suspend fun registrationUser(user: User)

}