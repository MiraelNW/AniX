package com.miraeldev.signin.domain

import com.miraeldev.signin.data.repositories.SignInRepository
import javax.inject.Inject

class LoginWithVkUseCase @Inject constructor(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(accessToken: String, userId: String) =
        repository.loginWithVk(accessToken, userId)

}
