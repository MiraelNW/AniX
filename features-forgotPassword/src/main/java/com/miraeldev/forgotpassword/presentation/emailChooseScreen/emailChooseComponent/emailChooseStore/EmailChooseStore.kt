package com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.emailChooseStore

import com.arkivanov.mvikotlin.core.store.Store
import com.miraeldev.forgotpassword.domain.models.EmailChooseErrorModel

interface EmailChooseStore
    : Store<EmailChooseStore.Intent, EmailChooseStore.State, EmailChooseStore.Label> {

    data class State(val email: String, val emailChooseErrorModel: EmailChooseErrorModel)

    sealed interface Label {
        data class OnEmailExist(val email: String) : Label
        data object OnBackClicked : Label
    }

    sealed interface Intent {
        data class OnEmailChange(val email: String) : Intent
        data object RefreshEmailError : Intent
        data object OnBackClicked : Intent
        data class CheckEmailExist(val email: String) : Intent
    }
}