package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke(otp:String) = repository.verifyOtp(otp)

}