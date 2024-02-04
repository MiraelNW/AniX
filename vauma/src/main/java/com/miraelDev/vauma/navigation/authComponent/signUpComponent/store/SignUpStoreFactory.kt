package com.miraeldev.navigation.decompose.authComponent.signUpComponent.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory

class SignUpStoreFactory {

    private val storeFactory = DefaultStoreFactory()

    fun create(): SignUpStore = object : SignUpStore,
        Store<SignUpStore.Intent, SignUpStore.State, SignUpStore.Label> by storeFactory.create(
            name = "SignUpStoreFactory",
            initialState = SignUpStore.State("", "", "", "", ""),
            reducer = ReducerImpl,
            executorFactory = ::ExecutorImpl
        ) {}


    private sealed interface Action {

    }

    private sealed interface Msg {
        data class ChangeImage(val image: String) : Msg
        data class ChangeUsername(val username: String) : Msg
        data class ChangeEmail(val email: String) : Msg
        data class ChangePassword(val password: String) : Msg
        data class ChangeRepeatedPassword(val repeatedPassword: String) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SignUpStore.Intent, Action, SignUpStore.State, Msg, SignUpStore.Label>() {
        override fun executeIntent(intent: SignUpStore.Intent, getState: () -> SignUpStore.State) {
            when (intent) {
                is SignUpStore.Intent.OnBackClick -> {
                    publish(SignUpStore.Label.OnBackClicked)
                }

                is SignUpStore.Intent.OnSignUpClick -> {
                    publish(SignUpStore.Label.OnSignUpClicked)
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
        }

    }

}