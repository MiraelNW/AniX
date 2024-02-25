package com.miraeldev.signin.presentation.signInComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.LogIn
import com.miraeldev.models.OnForgetPasswordClick
import com.miraeldev.models.OnSignUpClicked
import com.miraeldev.signin.presentation.signInComponent.store.SignInStoreFactory
import com.miraeldev.signin.presentation.store.SignInStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultSignInComponentFactory =
            (ComponentContext, OnSignUpClicked, OnForgetPasswordClick, LogIn) -> DefaultSignInComponent

@Inject
class DefaultSignInComponent(
    private val storeFactory: SignInStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted private val onSignUpClicked: () -> Unit,
    @Assisted private val onForgetPasswordClick: () -> Unit,
    @Assisted private val logIn: () -> Unit
) : SignInComponent, ComponentContext by componentContext {

    private val store: SignInStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SignInStore.Label.SignUpClicked -> {
                        onSignUpClicked()
                    }

                    is SignInStore.Label.ForgetPasswordClick -> {
                        onForgetPasswordClick()
                    }

                    is SignInStore.Label.LogIn -> {
                        logIn()
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

    override fun authViaGoogle(idToken: String) {
        store.accept(SignInStore.Intent.AuthViaGoogle(idToken))
    }

    override fun authViaVk(accessToken: String, userId: String, email: String?) {
        store.accept(SignInStore.Intent.AuthViaVk(accessToken, userId, email))
    }

    override fun refreshPasswordError() {
        store.accept(SignInStore.Intent.RefreshPasswordError)
    }

    override fun refreshEmailError() {
        store.accept(SignInStore.Intent.RefreshEmailError)
    }

    override fun forgetPasswordClick() {
        store.accept(SignInStore.Intent.ForgetPasswordClick)
    }
}