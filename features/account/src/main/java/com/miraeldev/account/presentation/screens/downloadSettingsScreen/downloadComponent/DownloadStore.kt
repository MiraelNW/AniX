package com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent

import android.os.Environment
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.account.domain.GetIsWifiUseCase
import com.miraeldev.account.domain.SetIsWifiOnlyUseCase
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadStore.Intent
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadStore.Label
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DownloadStore.State
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import java.io.File

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

@Inject
class DownloadStoreFactory(
    private val storeFactory: StoreFactory,
    private val setIsWifiOnlyUseCase: SetIsWifiOnlyUseCase,
    private val getIsWifiUseCase: GetIsWifiUseCase,
) {

    fun create(): DownloadStore =
        object :
            DownloadStore,
            Store<Intent, State, Label> by storeFactory.create(
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
                getIsWifiUseCase().collect { isSelected ->
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
                        setIsWifiOnlyUseCase(!intent.status)
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
}
