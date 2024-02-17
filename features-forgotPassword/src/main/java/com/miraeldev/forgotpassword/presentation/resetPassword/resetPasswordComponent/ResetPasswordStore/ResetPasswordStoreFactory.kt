package com.miraeldev.forgotpassword.presentation.resetPassword.resetPasswordComponent.ResetPasswordStore

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.forgotpassword.domain.usecases.SaveNewPasswordUseCase
import com.miraeldev.signin.domain.model.ResetPasswordErrorModel
import com.miraeldev.utils.ValidatePassword
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResetPasswordStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val validatePassword: ValidatePassword,
    private val saveNewPasswordUseCase: SaveNewPasswordUseCase
) {

    fun create(): ResetPasswordStore = object : ResetPasswordStore,
        Store<ResetPasswordStore.Intent, ResetPasswordStore.State, ResetPasswordStore.Label> by storeFactory.create(
            name = "ResetPasswordStoreFactory",
            initialState = ResetPasswordStore.State("", "", ResetPasswordErrorModel()),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}

    private sealed interface Msg {
        data class OnPasswordChange(val password: String) : Msg
        data class OnRepeatedPasswordChange(val repeatedPassword: String) : Msg
        data object RefreshPasswordError : Msg
        data object RefreshRepeatedPasswordError : Msg
        data class ResetPasswordError(val resetPasswordErrorModel: ResetPasswordErrorModel) : Msg
    }

    private sealed interface Action {

    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ResetPasswordStore.Intent, Action, ResetPasswordStore.State, Msg, ResetPasswordStore.Label>() {
        override fun executeIntent(
            intent: ResetPasswordStore.Intent,
            getState: () -> ResetPasswordStore.State
        ) {
            when (intent) {
                is ResetPasswordStore.Intent.OnPasswordChange -> {
                    dispatch(Msg.OnPasswordChange(intent.password))
                }

                is ResetPasswordStore.Intent.OnRepeatedPasswordChange -> {
                    dispatch(Msg.OnRepeatedPasswordChange(intent.repeatedPassword))
                }

                is ResetPasswordStore.Intent.RefreshPasswordError -> {
                    dispatch(Msg.RefreshPasswordError)
                }

                is ResetPasswordStore.Intent.RefreshRepeatedPasswordError -> {
                    dispatch(Msg.RefreshRepeatedPasswordError)
                }

                is ResetPasswordStore.Intent.SaveNewPassword -> {
                    val passwordState = validatePassword.execute(intent.password.trim())
                    val isPasswordsTheSame =
                        intent.password.trim() == intent.repeatedPassword.trim()
                    if (isPasswordsTheSame && passwordState.successful) {
                        scope.launch {
                            val isSuccess = saveNewPasswordUseCase(
                                email = intent.email,
                                password = intent.password
                            )
                            if (isSuccess.not()) {
                                dispatch(Msg.ResetPasswordError(ResetPasswordErrorModel(networkError = true)))
                            }
                        }
                    } else {
                        dispatch(
                            Msg.ResetPasswordError(
                                ResetPasswordErrorModel(
                                    passwordError = !passwordState.successful,
                                    repeatedPasswordError = !isPasswordsTheSame,
                                    passwordHasCapitalizedLetterError = !passwordState.hasCapitalizedLetter,
                                    passwordLengthError = !passwordState.hasMinimum,
                                    networkError = false
                                )
                            )
                        )
                    }
                }

                is ResetPasswordStore.Intent.OnBackClicked -> {
                    publish(ResetPasswordStore.Label.OnBackClicked)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ResetPasswordStore.State, Msg> {
        override fun ResetPasswordStore.State.reduce(msg: Msg): ResetPasswordStore.State =
            when (msg) {
                is Msg.OnPasswordChange -> {
                    copy(password = msg.password)
                }

                is Msg.OnRepeatedPasswordChange -> {
                    copy(repeatedPassword = msg.repeatedPassword)
                }

                is Msg.RefreshPasswordError -> {
                    copy(
                        resetPasswordErrorModel = resetPasswordErrorModel.copy(
                            passwordLengthError = false,
                            passwordError = false,
                            passwordHasCapitalizedLetterError = false
                        )
                    )
                }

                is Msg.RefreshRepeatedPasswordError -> {
                    copy(
                        resetPasswordErrorModel = resetPasswordErrorModel.copy(
                            repeatedPasswordError = false
                        )
                    )
                }

                is Msg.ResetPasswordError -> {
                    copy(resetPasswordErrorModel = msg.resetPasswordErrorModel)
                }
            }

    }

}