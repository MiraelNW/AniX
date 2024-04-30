package com.miraelDev.vauma.glue.signIn.repositories

import com.miraeldev.api.UserAuthDataRepository
import com.miraeldev.api.UserDataRepository
import com.miraeldev.signin.data.repositories.SignInRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SignInRepositoryImpl(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository
) : SignInRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        return userAuthDataRepository.signIn(email, password)
    }

    override suspend fun loginWithVk(accessToken: String, userId: String, email: String?) {
        userAuthDataRepository.loginWithVk(accessToken, userId, email)
    }

    override suspend fun getUserEmail(): String {
        return userDataRepository.getUserEmail().email
    }

    override suspend fun logInWithGoogle(idToken: String) {
        userAuthDataRepository.logInWithGoogle(idToken)
    }
}