package com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.resetPasswordStore.ResetPasswordStore
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.resetPasswordStore.ResetPasswordStoreFactory
import com.miraeldev.models.OnBackPressed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultResetPasswordComponentFactory = (ComponentContext, OnBackPressed) -> DefaultResetPasswordComponent

@Inject
class DefaultResetPasswordComponent(
    private val storeFactory: ResetPasswordStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit
) : ResetPasswordComponent, ComponentContext by componentContext {

    private val store: ResetPasswordStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    ResetPasswordStore.Label.OnBackClicked -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ResetPasswordStore.State>
        get() = store.stateFlow

    override fun onBackClicked() {
        store.accept(ResetPasswordStore.Intent.OnBackClicked)
    }

    override fun saveNewPassword(email: String, password: String, repeatedPassword: String) {
        store.accept(ResetPasswordStore.Intent.SaveNewPassword(email, password, repeatedPassword))
    }

    override fun onPasswordChange(password: String) {
        store.accept(ResetPasswordStore.Intent.OnPasswordChange(password))
    }

    override fun onRepeatedPasswordChange(repeatedPassword: String) {
        store.accept(ResetPasswordStore.Intent.OnRepeatedPasswordChange(repeatedPassword))
    }

    override fun refreshPasswordError() {
        store.accept(ResetPasswordStore.Intent.RefreshPasswordError)
    }

    override fun refreshRepeatedPasswordError() {
        store.accept(ResetPasswordStore.Intent.RefreshRepeatedPasswordError)
    }
}