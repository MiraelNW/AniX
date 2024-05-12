package com.miraeldev.signup.presentation.signUpScreen.signUpComponent

import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.store.SignUpStore
import kotlinx.coroutines.flow.StateFlow

interface SignUpComponent {

    val model: StateFlow<SignUpStore.State>

    fun onChangeImage(image: String)
    fun onChangeUsername(username: String)
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun onChangeRepeatedPassword(repeatedPassword: String)
    fun onSignUpClick(
        image: String,
        username: String,
        email: String,
        password: String,
        repeatedPassword: String
    )
    fun onBackClick()
    fun refreshPasswordError()
    fun refreshRepeatedPasswordError()
    fun refreshEmailError()
}