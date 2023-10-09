package com.miraelDev.vauma.domain.usecases.authUseCases

import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.repository.UserAuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: UserAuthRepository) {
    suspend operator fun invoke(user: User) = repository.signUp(user)
}