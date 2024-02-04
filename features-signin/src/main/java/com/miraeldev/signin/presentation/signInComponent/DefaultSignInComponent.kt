package com.miraeldev.navigation.decompose.authComponent.signInComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.signin.presentation.store.SignInStore
import com.miraeldev.signin.presentation.store.SignInStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSignInComponent(
    componentContext: ComponentContext,
    private val onSignUpClicked: () -> Unit,
) : SignInComponent, ComponentContext by componentContext {

    private val store: SignInStore = instanceKeeper.getStore { SignInStoreFactory().create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SignInStore.Label.SignUpClicked -> {
                        onSignUpClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SignInStore.State>
        get() = store.stateFlow

    override fun onSignInClick(email: String, password: String) {
        store.accept(SignInStore.Intent.SignIn(email, password))
    }

    override fun onSignUpClick() {
        store.accept(SignInStore.Intent.SignUp)
    }

    override fun onEmailChanged(email: String) {
        store.accept(SignInStore.Intent.ChangeEmail(email))
    }

    override fun onPasswordChanged(password: String) {
        store.accept(SignInStore.Intent.ChangePassword(password))
    }

    override fun refreshPasswordError() {
        TODO("Not yet implemented")
    }

    override fun refreshEmailError() {
        TODO("Not yet implemented")
    }
}