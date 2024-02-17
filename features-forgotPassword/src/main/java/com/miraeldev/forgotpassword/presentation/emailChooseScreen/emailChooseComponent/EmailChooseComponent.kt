package com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent

import com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.emailChooseStore.EmailChooseStore
import kotlinx.coroutines.flow.StateFlow

interface EmailChooseComponent {

    val model: StateFlow<EmailChooseStore.State>

    fun onEmailChange(email: String)
    fun refreshEmailError()
    fun checkEmailExist(email: String)
    fun onBackClicked()
}