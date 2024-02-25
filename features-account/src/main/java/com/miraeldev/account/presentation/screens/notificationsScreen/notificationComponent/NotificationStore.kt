package com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.NotificationStore.Intent
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.NotificationStore.Label
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.NotificationStore.State
import me.tatarka.inject.annotations.Inject

interface NotificationStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnBackClicked : Intent
    }

    data class State(val p: String = "")

    sealed interface Label {
        data object OnBackClicked : Label
    }
}

@Inject
class NotificationStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): NotificationStore =
        object : NotificationStore, Store<Intent, State, Label> by storeFactory.create(
            name = "NotificationStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnBackClicked -> publish(Label.OnBackClicked)
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = State()
    }
}
