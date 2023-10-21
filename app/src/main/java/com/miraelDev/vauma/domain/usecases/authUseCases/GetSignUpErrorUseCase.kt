package com.miraelDev.vauma.domain.usecases.authUseCases

import com.miraelDev.vauma.domain.models.user.User
import com.miraelDev.vauma.domain.repository.UserAuthRepository
import javax.inject.Inject

class GetSignUpErrorUseCase @Inject constructor(private val repository: UserAuthRepository) {
    operator fun invoke() = repository.getSignUpError()
}