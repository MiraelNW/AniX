package com.miraeldev.signup.presentation.codeVerifyScreen.codeVerifyComponent.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.models.user.User
import com.miraeldev.signup.domain.useCases.UpdateUserUseCase
import com.miraeldev.signup.domain.useCases.VerifyOtpCodeUseCase
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CodeVerifyStoreFactory(
    private val storeFactory: StoreFactory,
    private val updateUserUseCase: UpdateUserUseCase,
    private val verifyOtpCodeUseCase: VerifyOtpCodeUseCase
) {

    fun create(): CodeVerifyStore = object :
        CodeVerifyStore,
        Store<CodeVerifyStore.Intent, CodeVerifyStore.State, CodeVerifyStore.Label> by storeFactory.create(
            name = "CodeVerifyStoreFactory",
            initialState = CodeVerifyStore.State("", false),
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
        CoroutineExecutor<CodeVerifyStore.Intent, Action, CodeVerifyStore.State, Msg, CodeVerifyStore.Label>() {
        override fun executeIntent(
            intent: CodeVerifyStore.Intent,
            getState: () -> CodeVerifyStore.State
        ) {
            when (intent) {
                is CodeVerifyStore.Intent.SendNewOtp -> {
                    TODO()
                }

                is CodeVerifyStore.Intent.VerifyOtp -> {
                    scope.launch {
                        val isSuccess = verifyOtpCodeUseCase(
                            intent.otp,
                            User(email = intent.email, password = intent.password)
                        )
                        if (isSuccess.not()) {
                            dispatch(Msg.VerifyOtpError)
                        }
                    }
                }

                is CodeVerifyStore.Intent.RefreshError -> {
                    dispatch(Msg.RefreshOtpError)
                }

                is CodeVerifyStore.Intent.OnOtpChange -> {
                    dispatch(Msg.OnOtpChange(intent.otp))
                }

                is CodeVerifyStore.Intent.UpdateUser -> {
                    scope.launch {
                        updateUserUseCase(intent.email)
                    }
                }

                is CodeVerifyStore.Intent.OnBackClicked -> {
                    publish(CodeVerifyStore.Label.OnBackClicked)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<CodeVerifyStore.State, Msg> {
        override fun CodeVerifyStore.State.reduce(msg: Msg): CodeVerifyStore.State = when (msg) {
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