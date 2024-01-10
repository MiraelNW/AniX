package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import com.miraeldev.user.User
import javax.inject.Inject

class VerifyOtpCodeUseCase @Inject constructor(private val repository: SignUpRepository) {
    suspend operator fun invoke(otp: String, user: User) =
        repository.verifyOtpCode(otp, user)
}