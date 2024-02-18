package com.miraelDev.vauma.presentation.appRootComponent


import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraelDev.vauma.domain.usecases.CheckUserAuthStateUseCase
import com.miraelDev.vauma.domain.usecases.GetDarkThemeUseCase
import com.miraelDev.vauma.domain.usecases.GetUserAuthStateUseCase
import com.miraelDev.vauma.presentation.appRootComponent.MainStore.Intent
import com.miraelDev.vauma.presentation.appRootComponent.MainStore.Label
import com.miraelDev.vauma.presentation.appRootComponent.MainStore.State
import com.miraeldev.models.auth.AuthState
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object GetUserAuthState : Intent
    }

    data class State(val isDarkTheme: Boolean)

    sealed interface Label {
        data class UserAuthStateChecked(val authState: AuthState) : Label
    }
}

class MainStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val checkUserAuthState: CheckUserAuthStateUseCase,
    private val getUserAuthState: GetUserAuthStateUseCase,
    private val getDarkTheme: GetDarkThemeUseCase
) {

    fun create(): MainStore =
        object : MainStore, Store<Intent, State, Label> by storeFactory.create(
            name = "MainStore",
            initialState = State(false),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class UserAuthStateChecked(val authState: AuthState) : Action
        data class DarkThemeChanged(val isDarkTheme: Boolean) : Action
    }

    private sealed interface Msg {
        data class DarkThemeChanged(val isDarkTheme: Boolean) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getUserAuthState().collect {
                    dispatch(Action.UserAuthStateChecked(it))
                }
            }
            scope.launch {
                getDarkTheme().collect {
                    dispatch(Action.DarkThemeChanged(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.GetUserAuthState -> scope.launch { checkUserAuthState() }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.UserAuthStateChecked -> publish(Label.UserAuthStateChecked(action.authState))
                is Action.DarkThemeChanged -> dispatch(Msg.DarkThemeChanged(action.isDarkTheme))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.DarkThemeChanged -> copy(isDarkTheme = msg.isDarkTheme)
            }
    }
}
