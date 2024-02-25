package com.miraeldev.signup.domain.useCases

import com.miraeldev.signup.data.SignUpRepository
import com.miraeldev.user.User
import me.tatarka.inject.annotations.Inject

@Inject
class VerifyOtpCodeUseCase(private val repository: SignUpRepository) {
    suspend operator fun invoke(otp: String, user: User) =
        repository.verifyOtpCode(otp, user)
}