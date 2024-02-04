package com.miraeldev.navigation.decompose.authComponent.signUpComponent.store

import com.arkivanov.mvikotlin.core.store.Store

interface SignUpStore
    : Store<SignUpStore.Intent, SignUpStore.State, SignUpStore.Label> {

    data class State(
        val image: String,
        val username: String,
        val email: String,
        val password: String,
        val repeatedPassword: String,
    )

    sealed interface Label {
        object OnBackClicked: Label
        object OnSignUpClicked: Label
    }

    sealed interface Intent {

        data class ChangeUsername(val username: String) : Intent
        data class ChangeImage(val image: String) : Intent
        data class ChangeEmail(val email: String) : Intent
        data class ChangePassword(val password: String) : Intent
        data class ChangeRepeatedPassword(val repeatedPassword: String) : Intent
        object OnBackClick : Intent
        data class OnSignUpClick(val email: String, val password: String) : Intent
    }

}