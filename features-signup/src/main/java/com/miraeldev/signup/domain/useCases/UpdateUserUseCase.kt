package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val repository: SignUpRepository) {
    suspend operator fun invoke(email:String) = repository.updateUser(email)
}