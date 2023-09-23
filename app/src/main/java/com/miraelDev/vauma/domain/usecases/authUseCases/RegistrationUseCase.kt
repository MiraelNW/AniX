package com.miraelDev.vauma.domain.usecases.authUseCases

import com.miraelDev.vauma.domain.models.User
import com.miraelDev.vauma.domain.repository.RegistrationUserRepository
import com.miraelDev.vauma.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val repository: RegistrationUserRepository) {
    suspend operator fun invoke(user: User) = repository.registrationUser(user)
}