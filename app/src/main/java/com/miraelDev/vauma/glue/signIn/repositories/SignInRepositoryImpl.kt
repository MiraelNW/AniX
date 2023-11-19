package com.miraelDev.vauma.glue.signIn.repositories

import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.signin.data.repositories.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository
) : SignInRepository {
    override suspend fun signIn(username: String, password: String) {
        userAuthDataRepository.signIn(username, password)
    }

    override fun getSignInError(): Flow<Boolean> {
        return userAuthDataRepository.getSignInError()
    }

    override suspend fun loginWithVk(accessToken:String, userId:String) {
        userAuthDataRepository.loginWithVk(accessToken,userId)
    }

    override suspend fun getUserEmail(): String {
        return userDataRepository.getLocalUser().email
    }

    override suspend fun logInWithGoogle(idToken: String) {
        userAuthDataRepository.logInWithGoogle(idToken)
    }

}