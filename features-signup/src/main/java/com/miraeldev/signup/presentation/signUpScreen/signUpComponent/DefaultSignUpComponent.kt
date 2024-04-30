package com.miraeldev.signup.presentation.signUpScreen.signUpComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnBackPressed
import com.miraeldev.models.OnSignUp
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.SignUpComponent
import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.store.SignUpStore
import com.miraeldev.signup.presentation.signUpScreen.signUpComponent.store.SignUpStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultSignUpComponentFactory =
    (ComponentContext, OnBackPressed, OnSignUp) -> DefaultSignUpComponent

@Inject
class DefaultSignUpComponent(
    private val storeFactory: SignUpStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted private val onBackClicked: () -> Unit,
    @Assisted private val onSignUpClicked: (email: String, password: String) -> Unit,
) : SignUpComponent, ComponentContext by componentContext {

    private val store: SignUpStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SignUpStore.Label.OnSignUpClicked -> {
                        onSignUpClicked(it.email, it.password)
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

    override fun onSignUpClick(
        image: String,
        username: String,
        email: String,
        password: String,
        repeatedPassword: String
    ) {
        store.accept(
            SignUpStore.Intent.OnSignUpClick(
                image,
                username,
                email,
                password,
                repeatedPassword
            )
        )
    }

    override fun onBackClick() {
        store.accept(SignUpStore.Intent.OnBackClick)
    }

    override fun refreshPasswordError() {
        store.accept(SignUpStore.Intent.RefreshPasswordError)
    }

    override fun refreshRepeatedPasswordError() {
        store.accept(SignUpStore.Intent.RefreshRepeatedPasswordError)
    }

    override fun refreshEmailError() {
        store.accept(SignUpStore.Intent.RefreshEmailError)
    }
}