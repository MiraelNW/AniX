package com.miraeldev.navigation.decompose.authComponent.signInComponent

import com.miraeldev.signin.presentation.store.SignInStore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface SignInComponent {

    val model: StateFlow<SignInStore.State>

    fun onSignInClick(email: String, password: String)

    fun onSignUpClick()

    fun onEmailChanged(email: String)

    fun onPasswordChanged(password: String)

    fun refreshPasswordError()

    fun refreshEmailError()
}