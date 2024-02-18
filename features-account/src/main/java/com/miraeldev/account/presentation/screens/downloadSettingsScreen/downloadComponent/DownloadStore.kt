package com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent

import android.os.Environment
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.account.domain.GetPreferenceUseCase
import com.miraeldev.account.domain.SetPreferenceUseCase
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadStore.Intent
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadStore.Label
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadStore.State
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

interface DownloadStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnBackPressed : Intent
        data object DeleteAllVideos : Intent
        data class ChangeStatus(val status: Boolean) : Intent
    }

    data class State(val isSelected: Boolean)

    sealed interface Label {
        data object OnBackPressed : Label
    }
}

class DownloadStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val setPreferenceUseCase: SetPreferenceUseCase,
    private val getPreferenceUseCase: GetPreferenceUseCase,
) {

    fun create(): DownloadStore =
        object : DownloadStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DownloadStore",
            initialState = State(false),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class PreferenceLoaded(val isSelected: Boolean) : Action
    }

    private sealed interface Msg {
        data class PreferenceLoaded(val isSelected: Boolean) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getPreferenceUseCase(IS_WIFI_ONLY_KEY).collect { isSelected ->
                    dispatch(Action.PreferenceLoaded(isSelected))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnBackPressed -> publish(Label.OnBackPressed)
                is Intent.DeleteAllVideos -> {
                    val filePath =
                        Environment.getExternalStorageDirectory().absolutePath + "/Download/Vauma"
                    val file = File(filePath)
                    file.deleteRecursively()
                }

                is Intent.ChangeStatus -> {
                    scope.launch {
                        setPreferenceUseCase(IS_WIFI_ONLY_KEY, !intent.status)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.PreferenceLoaded -> dispatch(Msg.PreferenceLoaded(action.isSelected))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.PreferenceLoaded -> copy(isSelected = msg.isSelected)
            }
    }

    companion object {
        private const val IS_WIFI_ONLY_KEY = "is_wifi_only_key"
    }
}
