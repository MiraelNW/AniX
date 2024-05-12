package com.miraeldev.signin.presentation.signInComponent.store

import androidx.core.util.PatternsCompat
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.signin.domain.model.SignInErrorModel
import com.miraeldev.signin.domain.usecases.GetUserEmailUseCase
import com.miraeldev.signin.domain.usecases.LogInWithGoogleUseCase
import com.miraeldev.signin.domain.usecases.LoginWithVkUseCase
import com.miraeldev.signin.domain.usecases.SignInUseCase
import com.miraeldev.signin.presentation.store.SignInStore
import com.miraeldev.utils.ValidatePassword
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class SignInStoreFactory(
    private val storeFactory: StoreFactory,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val signInUseCase: SignInUseCase,
    private val loginWithVkUseCase: LoginWithVkUseCase,
    private val logInWithGoogleUseCase: LogInWithGoogleUseCase,
    private val validatePassword: ValidatePassword
) {

    fun create(): SignInStore = object :
        SignInStore,
        Store<SignInStore.Intent, SignInStore.State, SignInStore.Label> by storeFactory.create(
            name = "SignInStoreFactory",
            initialState = SignInStore.State(
                "",
                "",
                SignInErrorModel()
            ),
            bootstrapper = BootstrapperImpl(),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val savedEmail = getUserEmailUseCase()
                dispatch(Action.SavedEmailLoaded(savedEmail))
            }
        }
    }

    private sealed interface Action {
        data class SavedEmailLoaded(val email: String) : Action
    }

    private sealed interface Msg {
        data class ChangeEmail(val email: String) : Msg
        data class ChangePassword(val password: String) : Msg
        data object RefreshEmailError : Msg
        data object RefreshPasswordError : Msg
        data class SavedEmailLoaded(val email: String) : Msg
        data class SignInError(val signInError: SignInErrorModel) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SignInStore.Intent, Action, SignInStore.State, Msg, SignInStore.Label>() {
        override fun executeIntent(intent: SignInStore.Intent, getState: () -> SignInStore.State) {
            when (intent) {
                is SignInStore.Intent.ChangeEmail -> {
                    dispatch(Msg.ChangeEmail(email = intent.email))
                }

                is SignInStore.Intent.ChangePassword -> {
                    dispatch(Msg.ChangePassword(password = intent.password))
                }

                is SignInStore.Intent.AuthViaGoogle -> {
                    scope.launch { logInWithGoogleUseCase(intent.idToken) }
                }

                is SignInStore.Intent.AuthViaVk -> {
                    scope.launch {
                        loginWithVkUseCase(
                            intent.accessToken,
                            intent.userId,
                            intent.email
                        )
                    }
                }

                is SignInStore.Intent.SignIn -> {
                    val passwordState = validatePassword.execute(intent.password.trim())

                    val isEmailValid = PatternsCompat.EMAIL_ADDRESS.matcher(intent.email.trim()).matches()

                    if (passwordState.successful && isEmailValid) {
                        scope.launch {
                            val success = signInUseCase(intent.email, intent.password)
                            if (success) {
                                publish(SignInStore.Label.LogIn)
                            } else {
                                dispatch(
                                    Msg.SignInError(signInError = SignInErrorModel(networkError = true))
                                )
                            }
                        }
                    } else {
                        dispatch(
                            Msg.SignInError(
                                SignInErrorModel(
                                    emailError = !isEmailValid,
                                    passwordError = !passwordState.hasMinimum || !passwordState.hasCapitalizedLetter,
                                    passwordLengthError = !passwordState.hasMinimum,
                                    passwordHasCapitalizedLetterError = !passwordState.hasCapitalizedLetter,
                                )
                            )
                        )
                    }
                }

                is SignInStore.Intent.SignUp -> {
                    publish(SignInStore.Label.SignUpClicked)
                }

                is SignInStore.Intent.RefreshEmailError -> {
                    dispatch(Msg.RefreshEmailError)
                }

                is SignInStore.Intent.RefreshPasswordError -> {
                    dispatch(Msg.RefreshPasswordError)
                }

                is SignInStore.Intent.ForgetPasswordClick -> {
                    publish(SignInStore.Label.ForgetPasswordClick)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> SignInStore.State) {
            when (action) {
                is Action.SavedEmailLoaded -> {
                    dispatch(Msg.SavedEmailLoaded(action.email))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SignInStore.State, Msg> {
        override fun SignInStore.State.reduce(msg: Msg): SignInStore.State = when (msg) {
            is Msg.ChangePassword -> {
                copy(password = msg.password)
            }

            is Msg.ChangeEmail -> {
                copy(email = msg.email)
            }

            is Msg.RefreshPasswordError -> {
                copy(
                    signInError = signInError.copy(
                        networkError = false,
                        passwordError = false,
                        passwordHasCapitalizedLetterError = false,
                        passwordLengthError = false
                    )
                )
            }

            is Msg.RefreshEmailError -> {
                copy(signInError = signInError.copy(networkError = false, emailError = false))
            }

            is Msg.SavedEmailLoaded -> {
                copy(email = msg.email)
            }

            is Msg.SignInError -> {
                copy(signInError = msg.signInError)
            }
        }
    }
}