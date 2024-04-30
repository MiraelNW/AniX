package com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.resetPasswordStore

import com.arkivanov.mvikotlin.core.store.Store
import com.miraeldev.signin.domain.model.ResetPasswordErrorModel

interface ResetPasswordStore :
    Store<ResetPasswordStore.Intent, ResetPasswordStore.State, ResetPasswordStore.Label> {

    data class State(
        val password: String,
        val repeatedPassword: String,
        val resetPasswordErrorModel: ResetPasswordErrorModel
    )

    sealed interface Intent {
        data class SaveNewPassword(
            val email: String,
            val password: String,
            val repeatedPassword: String
        ) : Intent

        data class OnPasswordChange(val password: String) : Intent
        data class OnRepeatedPasswordChange(val repeatedPassword: String) : Intent
        data object RefreshPasswordError : Intent
        data object RefreshRepeatedPasswordError : Intent
        data object OnBackClicked : Intent
    }

    sealed interface Label {
        data object OnBackClicked : Label
    }
}