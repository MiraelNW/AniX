package com.miraeldev.navigation.decompose.authComponent.signUpComponent

import com.miraeldev.navigation.decompose.authComponent.signUpComponent.store.SignUpStore
import kotlinx.coroutines.flow.StateFlow

interface SignUpComponent {

    val model: StateFlow<SignUpStore.State>

    fun onChangeImage(image: String)
    fun onChangeUsername(username: String)
    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)
    fun onChangeRepeatedPassword(repeatedPassword: String)

    fun onSignUpClick(email: String, password: String)

    fun onBackClick()


}