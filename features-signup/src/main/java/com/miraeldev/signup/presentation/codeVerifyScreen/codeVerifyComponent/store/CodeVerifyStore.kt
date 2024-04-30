package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store

import com.arkivanov.mvikotlin.core.store.Store

interface CodeVerifyStore :
    Store<CodeVerifyStore.Intent, CodeVerifyStore.State, CodeVerifyStore.Label> {

    data class State(val otpText: String, val verifyOtpError: Boolean)

    sealed interface Intent {
        data class OnOtpChange(val otp: String) : Intent
        data class VerifyOtp(val otp: String, val email: String, val password: String) : Intent
        data class UpdateUser(val email: String) : Intent
        data object SendNewOtp : Intent
        data object RefreshError : Intent
        data object OnBackClicked : Intent
    }

    sealed interface Label {
        data object OnBackClicked : Label
    }
}