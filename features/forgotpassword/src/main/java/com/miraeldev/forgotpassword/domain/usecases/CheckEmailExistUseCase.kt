package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import me.tatarka.inject.annotations.Inject

@Inject
class CheckEmailExistUseCase(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke(email: String) = repository.checkEmailExist(email)
}