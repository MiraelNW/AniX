package com.miraelDev.vauma.glue.signIn.repositories

import com.miraeldev.UserAuthDataRepository
import com.miraeldev.UserDataRepository
import com.miraeldev.signin.data.repositories.SignInRepository
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val userAuthDataRepository: UserAuthDataRepository,
    private val userDataRepository: UserDataRepository
) : SignInRepository {
    override suspend fun signIn(email: String, password: String) {
        userAuthDataRepository.signIn(email, password)
    }

    override fun getSignInError(): Flow<Boolean> {
        return userAuthDataRepository.getSignInError()
    }

    override suspend fun checkVkAuthState() {
        userAuthDataRepository.checkVkAuthState()
    }

    override suspend fun getUserEmail(): String {
        return userDataRepository.getLocalUser().email
    }

}