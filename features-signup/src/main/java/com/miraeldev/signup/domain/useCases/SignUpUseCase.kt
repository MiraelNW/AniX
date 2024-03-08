package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import com.miraeldev.user.User
import me.tatarka.inject.annotations.Inject

@Inject
class SignUpUseCase(private val repository: SignUpRepository) {
    suspend operator fun invoke(user: User) = repository.signUp(user)
}