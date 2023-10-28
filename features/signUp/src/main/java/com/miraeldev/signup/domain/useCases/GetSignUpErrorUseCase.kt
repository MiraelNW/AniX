package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import javax.inject.Inject

class GetSignUpErrorUseCase @Inject constructor(private val repository: SignUpRepository) {
    operator fun invoke() = repository.getSignUpError()
}