package com.miraelDev.vauma.glue.signUp.repositories

import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.signup.data.SignUpRepository
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository,
) : SignUpRepository {

    override suspend fun signUp(user: User): Boolean {
        return userAuthDataRepository.signUp(user)
    }

    override suspend fun updateUser(email: String) {
        userDataRepository.updateUser(com.miraeldev.user.UserEmail(email))
    }

    override suspend fun verifyOtpCode(otpToken: String, user: User): Boolean {
        return userAuthDataRepository.verifyOtpCode(user = user, otpToken = otpToken)
    }

    override fun getSignUpError(): Flow<Boolean> {
        return userAuthDataRepository.getSignUpError()
    }

}