package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store.CodeVerifyStore
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store.CodeVerifyStoreFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCodeVerifyComponent @AssistedInject constructor(
    private val storeFactory: CodeVerifyStoreFactory,
    @Assisted("onBackClicked") onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : CodeVerifyComponent, ComponentContext by componentContext {

    private val store: CodeVerifyStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    CodeVerifyStore.Label.OnBackClicked -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CodeVerifyStore.State>
        get() = store.stateFlow

    override fun onOtpChange(otp: String) {
        store.accept(CodeVerifyStore.Intent.OnOtpChange(otp))
    }

    override fun verifyOtp(otp: String, email: String, password: String) {
        store.accept(CodeVerifyStore.Intent.VerifyOtp(otp, email, password))
    }

    override fun updateUser(email: String) {
        store.accept(CodeVerifyStore.Intent.UpdateUser(email))
    }

    override fun sendNewOtp() {
        store.accept(CodeVerifyStore.Intent.SendNewOtp)
    }

    override fun refreshError() {
        store.accept(CodeVerifyStore.Intent.RefreshError)
    }

    override fun onBackClicked() {
        store.accept(CodeVerifyStore.Intent.OnBackClicked)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultCodeVerifyComponent
    }
}