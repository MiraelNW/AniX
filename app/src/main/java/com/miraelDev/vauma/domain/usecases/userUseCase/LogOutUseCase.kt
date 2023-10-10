package com.miraelDev.vauma.domain.usecases.userUseCase

import com.miraelDev.vauma.domain.repository.UserRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.setUserUnAuthorizedStatus()
}