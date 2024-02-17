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
    override suspend fun signIn(email: String, password: String): Boolean {
        return userAuthDataRepository.signIn(email, password)
    }

    override fun getSignInError(): Flow<Boolean> {
        return userAuthDataRepository.getSignInError()
    }

    override suspend fun loginWithVk(accessToken:String, userId:String,email:String?) {
        userAuthDataRepository.loginWithVk(accessToken,userId,email)
    }

    override suspend fun getUserEmail(): String {
        return userDataRepository.getUserEmail().email
    }

    override suspend fun logInWithGoogle(idToken: String) {
        userAuthDataRepository.logInWithGoogle(idToken)
    }

}