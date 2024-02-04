package com.miraeldev.signin.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.serialization.Serializable

interface SignInStore : Store<SignInStore.Intent, SignInStore.State, SignInStore.Label> {

    @Serializable
    data class State(
        val email: String,
        val password: String,
        val isEmailError: Boolean,
        val isPasswordError: Boolean,
        val isSignInError: Boolean
    )

    sealed interface Label {
        object SignUpClicked : Label
    }

    sealed interface Intent {
        data class ChangeEmail(val email: String): Intent
        data class ChangePassword(val password: String): Intent
        data class SignIn(val email: String, val password: String): Intent
        object SignUp: Intent
        object RefreshPasswordError: Intent
        object RefreshEmailError: Intent
    }


}