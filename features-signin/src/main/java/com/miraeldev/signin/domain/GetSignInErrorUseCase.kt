package com.miraeldev.signin.domain

import com.miraeldev.signin.data.repositories.SignInRepository
import javax.inject.Inject

class GetSignInErrorUseCase @Inject constructor(private val repository: SignInRepository) {
    operator fun invoke() = repository.getSignInError()
}