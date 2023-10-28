package com.miraelDev.vauma.glue.signUp.repositories

import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.user.LocalUser
import com.miraeldev.signup.data.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository,
) : SignUpRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        imagePath: String
    ) {
        userAuthDataRepository.signUp(name, email, password, imagePath)
    }

    override suspend fun updateUser(email: String) {
        userDataRepository.updateUser(com.miraeldev.user.LocalUser(email))
    }

    override fun getSignUpError(): Flow<Boolean> {
        return userAuthDataRepository.getSignUpError()
    }

}