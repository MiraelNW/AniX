package com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.store

import com.arkivanov.mvikotlin.core.store.Store

interface CodeVerifyRPStore :
    Store<CodeVerifyRPStore.Intent, CodeVerifyRPStore.State, CodeVerifyRPStore.Label> {

    data class State(val otpText: String, val verifyOtpError: Boolean)

    sealed interface Intent {
        data class OnOtpChange(val otp: String) : Intent
        data class VerifyOtp(val otp: String) : Intent
        data object SendNewOtp : Intent
        data object RefreshError : Intent
        data object OnBackClicked : Intent
    }

    sealed interface Label {
        data object OnBackClicked : Label
        data object OtpVerified : Label
    }
}