package com.miraelDev.vauma.domain.usecases.authUseCases

import com.miraelDev.vauma.domain.repository.UserAuthRepository
import com.miraelDev.vauma.domain.repository.UserRepository
import javax.inject.Inject

class GetUserAuthStateUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke() = repository.getUserStatus()
}