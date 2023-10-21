package com.miraelDev.vauma.domain.usecases.authUseCases

import com.miraelDev.vauma.domain.repository.UserAuthRepository
import javax.inject.Inject

class CheckVkAuthStateUseCase @Inject constructor(
    private val repository: UserAuthRepository
) {

    suspend operator fun invoke() {
        repository.checkVkAuthState()
    }
}
