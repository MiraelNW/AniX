package com.miraeldev.signin.presentation.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.miraeldev.signin.domain.SignInUseCase
import com.miraeldev.utils.ValidatePassword

class SignInStoreFactory() {

    private val storeFactory = DefaultStoreFactory()

    fun create(): SignInStore = object : SignInStore,
        Store<SignInStore.Intent, SignInStore.State, SignInStore.Label> by storeFactory.create(
            name = "SignInStoreFactory",
            initialState = SignInStore.State(
                "",
                "",
                false,
                false,
                false
            ),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}


    private sealed interface Action {

    }

    private sealed interface Msg {
        data class ChangeEmail(val email: String) : Msg
        data class ChangePassword(val password: String) : Msg
        data object RefreshEmailError : Msg
        data object RefreshPasswordError : Msg
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

                is SignInStore.Intent.SignIn -> {
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
                copy(isPasswordError = false)
            }

            is Msg.RefreshEmailError -> {
                copy(isEmailError = false)
            }
        }
    }
}