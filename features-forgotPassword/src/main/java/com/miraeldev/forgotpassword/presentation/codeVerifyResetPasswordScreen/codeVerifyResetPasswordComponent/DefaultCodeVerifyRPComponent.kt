package com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.store.CodeVerifyRPStore
import com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.store.CodeVerifyStoreRPFactory
import com.miraeldev.models.OnBackPressed
import com.miraeldev.models.OnOtpVerified
import com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.CodeVerifyRPComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultCodeVerifyRPComponentFactory =
            (ComponentContext, OnBackPressed, OnOtpVerified) -> DefaultCodeVerifyRPComponent

@Inject
class DefaultCodeVerifyRPComponent(
    private val storeFactory: CodeVerifyStoreRPFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onBackClicked: () -> Unit,
    @Assisted onOtpVerified: () -> Unit
) : CodeVerifyRPComponent, ComponentContext by componentContext {

    private val store: CodeVerifyRPStore = instanceKeeper.getStore { storeFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    CodeVerifyRPStore.Label.OnBackClicked -> {
                        onBackClicked()
                    }
                    CodeVerifyRPStore.Label.OtpVerified -> {
                        onOtpVerified()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CodeVerifyRPStore.State>
        get() = store.stateFlow

    override fun onOtpChange(otp: String) {
        store.accept(CodeVerifyRPStore.Intent.OnOtpChange(otp))
    }

    override fun verifyOtp(otp: String) {
        store.accept(CodeVerifyRPStore.Intent.VerifyOtp(otp))
    }

    override fun sendNewOtp() {
        store.accept(CodeVerifyRPStore.Intent.SendNewOtp)
    }

    override fun refreshError() {
        store.accept(CodeVerifyRPStore.Intent.RefreshError)
    }

    override fun onBackClicked() {
        store.accept(CodeVerifyRPStore.Intent.OnBackClicked)
    }
}