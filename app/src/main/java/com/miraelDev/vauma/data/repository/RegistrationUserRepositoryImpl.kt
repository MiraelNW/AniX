package com.miraelDev.vauma.data.repository

import com.miraelDev.vauma.domain.models.User
import com.miraelDev.vauma.domain.repository.RegistrationUserRepository
import javax.inject.Inject

class RegistrationUserRepositoryImpl @Inject constructor(

) : RegistrationUserRepository{

    override suspend fun registrationUser(user: User) {
        //TODO("Not yet implemented")
    }

}