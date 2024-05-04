package com.miraeldev.signin.domain.usecases

import com.miraeldev.signin.data.repositories.SignInRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoginWithVkUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(accessToken: String, userId: String, email: String?) =
        repository.loginWithVk(accessToken, userId, email)
}
