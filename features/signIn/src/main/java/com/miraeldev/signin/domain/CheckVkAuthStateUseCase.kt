package com.miraeldev.signin.domain

import com.miraeldev.signin.data.repositories.SignInRepository
import javax.inject.Inject

class CheckVkAuthStateUseCase @Inject constructor(
    private val repository: SignInRepository
) {
    suspend operator fun invoke() = repository.checkVkAuthState()

}
