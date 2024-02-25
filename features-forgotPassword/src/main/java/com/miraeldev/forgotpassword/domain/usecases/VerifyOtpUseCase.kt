package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import me.tatarka.inject.annotations.Inject

@Inject
class VerifyOtpUseCase(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke(otp:String) = repository.verifyOtp(otp)

}