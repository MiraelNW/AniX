package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import me.tatarka.inject.annotations.Inject

@Inject
class UpdateUserUseCase(private val repository: SignUpRepository) {
    suspend operator fun invoke(email:String) = repository.updateUser(email)
}