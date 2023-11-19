package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import javax.inject.Inject

class GetRegistrationCompleteUseCase @Inject constructor(private val repository: SignUpRepository) {
    operator fun invoke() = repository.getRegistrationCompleteResult()
}