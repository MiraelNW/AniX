package com.miraelDev.vauma.domain.usecases.userUseCase

import com.miraelDev.vauma.domain.models.user.LocalUser
import com.miraelDev.vauma.domain.repository.UserAuthRepository
import com.miraelDev.vauma.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.getLocalUser()
}