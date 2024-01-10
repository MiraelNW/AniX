package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import com.miraeldev.user.User
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {
    suspend operator fun invoke(user: User) = repository.signUp(user)
}