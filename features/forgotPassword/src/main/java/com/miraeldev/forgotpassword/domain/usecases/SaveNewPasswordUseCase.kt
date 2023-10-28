package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import javax.inject.Inject

class SaveNewPasswordUseCase @Inject constructor(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke(newPassword: String) = repository.saveNewPassword(newPassword)

}