package com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordStore.ResetPasswordStore
import com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordStore.ResetPasswordStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultResetPasswordComponent @AssistedInject constructor(
    private val storeFactory: ResetPasswordStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onBackClicked") onBackClicked: () -> Unit
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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onBackClicked") onBackClicked: () -> Unit
        ): DefaultResetPasswordComponent
    }


}