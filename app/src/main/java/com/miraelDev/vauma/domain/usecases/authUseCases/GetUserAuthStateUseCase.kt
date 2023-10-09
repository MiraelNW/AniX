package com.miraelDev.vauma.domain.usecases.authUseCases

import com.miraelDev.vauma.domain.repository.UserAuthRepository
import javax.inject.Inject

class GetUserAuthStateUseCase @Inject constructor(private val repository: UserAuthRepository) {
    operator fun invoke() = repository.getUserAuthStatus()
}