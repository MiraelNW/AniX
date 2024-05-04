package com.miraeldev.signup.presentation.signUpScreen.signUpComponent.store

import com.arkivanov.mvikotlin.core.store.Store
import com.miraeldev.signin.domain.model.SignUpErrorModel

interface SignUpStore :
    Store<SignUpStore.Intent, SignUpStore.State, SignUpStore.Label> {

    data class State(
        val image: String,
        val username: String,
        val email: String,
        val password: String,
        val repeatedPassword: String,
        val signUpError: SignUpErrorModel
    )

    sealed interface Label {
        data object OnBackClicked : Label
        data class OnSignUpClicked(val email: String, val password: String) : Label
    }

    sealed interface Intent {

        data class ChangeUsername(val username: String) : Intent
        data class ChangeImage(val image: String) : Intent
        data class ChangeEmail(val email: String) : Intent
        data class ChangePassword(val password: String) : Intent
        data class ChangeRepeatedPassword(val repeatedPassword: String) : Intent
        data object OnBackClick : Intent
        data object RefreshPasswordError : Intent
        data object RefreshRepeatedPasswordError : Intent
        data object RefreshEmailError : Intent
        data class OnSignUpClick(
            val image: String,
            val username: String,
            val email: String,
            val password: String,
            val repeatedPassword: String
        ) : Intent
    }
}