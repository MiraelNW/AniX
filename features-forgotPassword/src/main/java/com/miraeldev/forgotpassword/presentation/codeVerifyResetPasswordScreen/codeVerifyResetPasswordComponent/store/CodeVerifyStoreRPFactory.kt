package com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.codeVerifyResetPasswordComponent.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.forgotpassword.domain.usecases.VerifyOtpUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CodeVerifyStoreRPFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val verifyOtpCodeUseCase: VerifyOtpUseCase
) {

    fun create(): CodeVerifyRPStore = object : CodeVerifyRPStore,
        Store<CodeVerifyRPStore.Intent, CodeVerifyRPStore.State, CodeVerifyRPStore.Label> by storeFactory.create(
            name = "CodeVerifyStoreFactory",
            initialState = CodeVerifyRPStore.State("", false),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}


    private sealed interface Msg {
        data object SendNewOtp : Msg

        data object VerifyOtpError : Msg

        data class OnOtpChange(val otp: String) : Msg
        data object RefreshOtpError : Msg
    }

    private sealed interface Action

    private inner class ExecutorImpl :
        CoroutineExecutor<CodeVerifyRPStore.Intent, Action, CodeVerifyRPStore.State, Msg, CodeVerifyRPStore.Label>() {
        override fun executeIntent(
            intent: CodeVerifyRPStore.Intent,
            getState: () -> CodeVerifyRPStore.State
        ) {
            when (intent) {
                is CodeVerifyRPStore.Intent.SendNewOtp -> {
                    TODO()
                }

                is CodeVerifyRPStore.Intent.VerifyOtp -> {
                    scope.launch {
                        val isSuccess = verifyOtpCodeUseCase(intent.otp)
                        if (isSuccess) {
                            publish(CodeVerifyRPStore.Label.OtpVerified)
                        } else {
                            dispatch(Msg.VerifyOtpError)
                        }
                    }
                }

                is CodeVerifyRPStore.Intent.RefreshError -> {
                    dispatch(Msg.RefreshOtpError)
                }

                is CodeVerifyRPStore.Intent.OnOtpChange -> {
                    dispatch(Msg.OnOtpChange(intent.otp))
                }

                is CodeVerifyRPStore.Intent.OnBackClicked -> {
                    publish(CodeVerifyRPStore.Label.OnBackClicked)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<CodeVerifyRPStore.State, Msg> {
        override fun CodeVerifyRPStore.State.reduce(msg: Msg): CodeVerifyRPStore.State = when (msg) {
            is Msg.SendNewOtp -> {
                TODO()
            }

            is Msg.VerifyOtpError -> {
                copy(verifyOtpError = true)
            }

            is Msg.OnOtpChange -> {
                copy(otpText = msg.otp)
            }

            is Msg.RefreshOtpError -> {
                copy(verifyOtpError = false)
            }
        }
    }
}