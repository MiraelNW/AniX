package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent

import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store.CodeVerifyStore
import kotlinx.coroutines.flow.StateFlow

interface CodeVerifyComponent {

    val model: StateFlow<CodeVerifyStore.State>

    fun onOtpChange(otp: String)
    fun verifyOtp(otp: String, email: String, password: String)
    fun updateUser(email: String)
    fun sendNewOtp()
    fun refreshError()

    fun onBackClicked()
}