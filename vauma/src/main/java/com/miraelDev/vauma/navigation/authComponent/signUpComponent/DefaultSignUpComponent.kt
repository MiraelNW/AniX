package com.miraeldev.navigation.decompose.authComponent.signUpComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.navigation.decompose.extensions.componentScope
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.store.SignUpStore
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.store.SignUpStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSignUpComponent(
    componentContext: ComponentContext,
    private val onBackClicked: () -> Unit,
    private val onSignUpClicked: () -> Unit,
) : SignUpComponent, ComponentContext by componentContext {

    private val store: SignUpStore = instanceKeeper.getStore { SignUpStoreFactory().create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SignUpStore.Label.OnSignUpClicked -> {
                        onSignUpClicked()
                    }
                    is SignUpStore.Label.OnBackClicked -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SignUpStore.State>
        get() = store.stateFlow

    override fun onChangeImage(image: String) {
        store.accept(SignUpStore.Intent.ChangeImage(image))
    }

    override fun onChangeUsername(username: String) {
        store.accept(SignUpStore.Intent.ChangeUsername(username))
    }

    override fun onChangeEmail(email: String) {
        store.accept(SignUpStore.Intent.ChangeEmail(email))
    }

    override fun onChangePassword(password: String) {
        store.accept(SignUpStore.Intent.ChangePassword(password))
    }

    override fun onChangeRepeatedPassword(repeatedPassword: String) {
        store.accept(SignUpStore.Intent.ChangeRepeatedPassword(repeatedPassword))
    }

    override fun onSignUpClick(email: String, password: String) {
        store.accept(SignUpStore.Intent.OnSignUpClick(email, password))
    }

    override fun onBackClick() {
        store.accept(SignUpStore.Intent.OnBackClick)
    }
}