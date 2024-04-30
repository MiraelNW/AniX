package com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent

import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.resetPasswordStore.ResetPasswordStore
import kotlinx.coroutines.flow.StateFlow

interface ResetPasswordComponent {

    val model: StateFlow<ResetPasswordStore.State>

    fun onBackClicked()
    fun saveNewPassword(email: String, password: String, repeatedPassword: String)
    fun onPasswordChange(password: String)
    fun onRepeatedPasswordChange(repeatedPassword: String)
    fun refreshPasswordError()
    fun refreshRepeatedPasswordError()
}