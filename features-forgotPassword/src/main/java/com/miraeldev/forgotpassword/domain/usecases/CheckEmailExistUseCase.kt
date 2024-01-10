package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import javax.inject.Inject

class CheckEmailExistUseCase @Inject constructor(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke(email: String) = repository.checkEmailExist(email)

}