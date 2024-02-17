package com.miraeldev.signin.domain.usecases

import com.miraeldev.signin.data.repositories.SignInRepository
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(private val repository: SignInRepository) {
    suspend operator fun invoke() = repository.getUserEmail()
}