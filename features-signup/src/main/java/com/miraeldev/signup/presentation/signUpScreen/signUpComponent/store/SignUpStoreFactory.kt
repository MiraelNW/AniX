package com.miraeldev.signup.presentation.signUpScreen.signUpComponent.store

import android.util.Patterns
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.models.user.User
import com.miraeldev.signin.domain.model.SignUpErrorModel
import com.miraeldev.signup.domain.useCases.SignUpUseCase
import com.miraeldev.utils.ValidatePassword
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class SignUpStoreFactory(
    private val storeFactory: StoreFactory,
    private val signUpUseCase: SignUpUseCase,
    private val validatePassword: ValidatePassword
) {

    fun create(): SignUpStore = object :
        SignUpStore,
        Store<SignUpStore.Intent, SignUpStore.State, SignUpStore.Label> by storeFactory.create(
            name = "SignUpStoreFactory",
            initialState = SignUpStore.State("", "", "", "", "", SignUpErrorModel()),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeImage(val image: String) : Msg
        data class ChangeUsername(val username: String) : Msg
        data class ChangeEmail(val email: String) : Msg
        data class ChangePassword(val password: String) : Msg
        data class ChangeRepeatedPassword(val repeatedPassword: String) : Msg
        data class RegistrationError(val signUpErrorModel: SignUpErrorModel) : Msg
        data object RefreshEmailError : Msg
        data object RefreshPasswordError : Msg
        data object RefreshRepeatedPasswordError : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SignUpStore.Intent, Action, SignUpStore.State, Msg, SignUpStore.Label>() {
        override fun executeIntent(intent: SignUpStore.Intent, getState: () -> SignUpStore.State) {
            when (intent) {
                is SignUpStore.Intent.OnBackClick -> {
                    publish(SignUpStore.Label.OnBackClicked)
                }

                is SignUpStore.Intent.OnSignUpClick -> {
                    val passwordState = validatePassword.execute(intent.password.trim())
                    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(intent.email.trim()).matches()
                    val isPasswordsTheSame =
                        intent.password.trim() == intent.repeatedPassword.trim()
                    if (isEmailValid && isPasswordsTheSame && passwordState.successful) {
                        scope.launch {
                            val isRegistrationSuccess = signUpUseCase(
                                User(
                                    image = intent.image,
                                    username = intent.username,
                                    email = intent.email,
                                    password = intent.password
                                )
                            )
                            if (isRegistrationSuccess) {
                                publish(
                                    SignUpStore.Label.OnSignUpClicked(
                                        intent.email,
                                        intent.password
                                    )
                                )
                            } else {
                                dispatch(Msg.RegistrationError(SignUpErrorModel(networkError = true)))
                            }
                        }
                    } else {
                        dispatch(
                            Msg.RegistrationError(
                                SignUpErrorModel(
                                    emailError = !isEmailValid,
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

                is SignUpStore.Intent.ChangeImage -> {
                    dispatch(Msg.ChangeImage(image = intent.image))
                }

                is SignUpStore.Intent.ChangeUsername -> {
                    dispatch(Msg.ChangeUsername(username = intent.username))
                }

                is SignUpStore.Intent.ChangeEmail -> {
                    dispatch(Msg.ChangeEmail(email = intent.email))
                }

                is SignUpStore.Intent.ChangePassword -> {
                    dispatch(Msg.ChangePassword(password = intent.password))
                }

                is SignUpStore.Intent.ChangeRepeatedPassword -> {
                    dispatch(Msg.ChangeRepeatedPassword(repeatedPassword = intent.repeatedPassword))
                }

                is SignUpStore.Intent.RefreshEmailError -> {
                    dispatch(Msg.RefreshEmailError)
                }

                is SignUpStore.Intent.RefreshPasswordError -> {
                    dispatch(Msg.RefreshPasswordError)
                }

                is SignUpStore.Intent.RefreshRepeatedPasswordError -> {
                    dispatch(Msg.RefreshRepeatedPasswordError)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<SignUpStore.State, Msg> {
        override fun SignUpStore.State.reduce(msg: Msg): SignUpStore.State = when (msg) {
            is Msg.ChangeImage -> {
                copy(image = msg.image)
            }

            is Msg.ChangeUsername -> {
                copy(username = msg.username)
            }

            is Msg.ChangeEmail -> {
                copy(email = msg.email)
            }

            is Msg.ChangePassword -> {
                copy(password = msg.password)
            }

            is Msg.ChangeRepeatedPassword -> {
                copy(repeatedPassword = msg.repeatedPassword)
            }

            is Msg.RefreshEmailError -> {
                copy(signUpError = signUpError.copy(emailError = false))
            }

            is Msg.RefreshPasswordError -> {
                copy(
                    signUpError = signUpError.copy(
                        passwordError = false,
                        passwordHasCapitalizedLetterError = false,
                        passwordLengthError = false
                    )
                )
            }

            is Msg.RefreshRepeatedPasswordError -> {
                copy(signUpError = signUpError.copy(repeatedPasswordError = false))
            }

            is Msg.RegistrationError -> {
                copy(signUpError = msg.signUpErrorModel)
            }
        }
    }
}