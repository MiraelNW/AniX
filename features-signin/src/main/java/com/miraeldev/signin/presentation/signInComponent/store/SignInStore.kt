package com.miraeldev.signin.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.miraeldev.signin.domain.model.SignInErrorModel
import kotlinx.serialization.Serializable

interface SignInStore : Store<SignInStore.Intent, SignInStore.State, SignInStore.Label> {

    @Serializable
    data class State(
        val email: String,
        val password: String,
        val signInError: SignInErrorModel
    )

    sealed interface Label {
        data object SignUpClicked : Label
        data object ForgetPasswordClick : Label
        data object LogIn : Label
    }

    sealed interface Intent {
        data class ChangeEmail(val email: String) : Intent
        data class ChangePassword(val password: String) : Intent
        data class AuthViaGoogle(val idToken: String) : Intent
        data class AuthViaVk(val accessToken: String, val userId: String, val email: String?) : Intent
        data class SignIn(val email: String, val password: String) : Intent
        data object SignUp : Intent
        data object RefreshPasswordError : Intent
        data object RefreshEmailError : Intent
        data object ForgetPasswordClick : Intent
    }
}