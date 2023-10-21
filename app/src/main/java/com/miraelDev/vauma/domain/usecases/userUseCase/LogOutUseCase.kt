package com.miraelDev.vauma.domain.usecases.userUseCase

import com.miraelDev.vauma.domain.repository.UserAuthRepository
import com.miraelDev.vauma.domain.repository.UserRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: UserAuthRepository) {
    suspend operator fun invoke() = repository.logOutUser()
}