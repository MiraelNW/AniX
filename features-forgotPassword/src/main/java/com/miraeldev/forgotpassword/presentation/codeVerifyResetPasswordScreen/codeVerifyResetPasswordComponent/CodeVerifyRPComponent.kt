package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent

import com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.store.CodeVerifyRPStore
import kotlinx.coroutines.flow.StateFlow

interface CodeVerifyRPComponent {

    val model: StateFlow<CodeVerifyRPStore.State>

    fun onOtpChange(otp: String)
    fun verifyOtp(otp: String)
    fun sendNewOtp()
    fun refreshError()

    fun onBackClicked()


}