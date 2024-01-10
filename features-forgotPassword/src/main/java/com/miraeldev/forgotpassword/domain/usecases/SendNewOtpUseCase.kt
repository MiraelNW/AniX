package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import javax.inject.Inject

class SendNewOtpUseCase @Inject constructor(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke() = repository.sendNewOtp()

}