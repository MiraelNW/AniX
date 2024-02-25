package com.miraeldev.forgotpassword.domain.usecases

import com.miraeldev.forgotpassword.data.ForgotPasswordRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveNewPasswordUseCase(private val repository: ForgotPasswordRepository) {

    suspend operator fun invoke(email: String, password: String) =
        repository.saveNewPassword(email, password)

}