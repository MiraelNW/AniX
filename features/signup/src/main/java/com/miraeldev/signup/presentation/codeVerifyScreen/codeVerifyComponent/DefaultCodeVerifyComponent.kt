package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnBackPressed
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store.CodeVerifyStore
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store.CodeVerifyStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultCodeVerifyComponentFactory = (ComponentContext, OnBackPressed) -> DefaultCodeVerifyComponent

@Inject
class DefaultCodeVerifyComponent(
    private val storeFactory: CodeVerifyStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit
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
}