package com.miraeldev.forgotpassword.presentation.emailChooseScreen.emailChooseComponent.emailChooseStore

import android.util.Patterns
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.forgotpassword.domain.models.EmailChooseErrorModel
import com.miraeldev.forgotpassword.domain.usecases.CheckEmailExistUseCase
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class EmailChooseStoreFactory(
    private val storeFactory: StoreFactory,
    private val checkEmailExistUseCase: CheckEmailExistUseCase
) {

    fun create(): EmailChooseStore = object : EmailChooseStore,
        Store<EmailChooseStore.Intent, EmailChooseStore.State, EmailChooseStore.Label> by storeFactory.create(
            name = "EmailChooseStoreFactory",
            initialState = EmailChooseStore.State("", EmailChooseErrorModel()),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class OnEmailChange(val email: String) : Msg
        data object RefreshEmailError : Msg
        data class EmailChooseError(val emailChooseError: EmailChooseErrorModel) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<EmailChooseStore.Intent, Action, EmailChooseStore.State, Msg, EmailChooseStore.Label>() {
        override fun executeIntent(
            intent: EmailChooseStore.Intent,
            getState: () -> EmailChooseStore.State
        ) {
            when (intent) {
                is EmailChooseStore.Intent.OnEmailChange -> {
                    dispatch(Msg.OnEmailChange(intent.email))
                }

                is EmailChooseStore.Intent.CheckEmailExist -> {
                    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(intent.email.trim()).matches()
                    if (isEmailValid) {
                        scope.launch {
                            val isSuccess = checkEmailExistUseCase(intent.email)
                            if (isSuccess) {
                                publish(EmailChooseStore.Label.OnEmailExist(intent.email))
                            } else {
                                dispatch(
                                    Msg.EmailChooseError(
                                        emailChooseError = EmailChooseErrorModel(
                                            emailNotExistError = true
                                        )
                                    )
                                )
                            }
                        }
                    } else {
                        dispatch(
                            Msg.EmailChooseError(
                                emailChooseError = EmailChooseErrorModel(
                                    emailNotValidError = true
                                )
                            )
                        )
                    }
                }

                is EmailChooseStore.Intent.OnBackClicked -> {
                    publish(EmailChooseStore.Label.OnBackClicked)
                }

                is EmailChooseStore.Intent.RefreshEmailError -> {
                    dispatch(Msg.RefreshEmailError)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<EmailChooseStore.State, Msg> {
        override fun EmailChooseStore.State.reduce(msg: Msg): EmailChooseStore.State = when (msg) {
            is Msg.OnEmailChange -> {
                copy(email = msg.email)
            }

            is Msg.RefreshEmailError -> {
                copy(
                    emailChooseErrorModel = emailChooseErrorModel.copy(
                        emailNotExistError = false,
                        emailNotValidError = false
                    )
                )
            }

            is Msg.EmailChooseError -> {
                copy(emailChooseErrorModel = msg.emailChooseError)
            }
        }
    }


}