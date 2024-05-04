package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SendNewOtpUseCase(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke() = repository.sendNewOtp()
}