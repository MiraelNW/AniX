package com.miraeldev.video.presentation.videoComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.video.PlayerWrapper
import com.miraeldev.video.domain.useCases.GetVideoInfoUseCase
import com.miraeldev.video.domain.useCases.LoadNextEpisodeUseCase
import com.miraeldev.video.domain.useCases.LoadPreviousEpisodeUseCase
import com.miraeldev.video.domain.useCases.LoadSpecificEpisodeUseCase
import com.miraeldev.video.presentation.videoComponent.VideoStore.Intent
import com.miraeldev.video.presentation.videoComponent.VideoStore.Label
import com.miraeldev.video.presentation.videoComponent.VideoStore.State
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface VideoStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object LoadNextVideo : Intent
        data object LoadPreviousVideo : Intent
        data class LoadVideoSelectedQuality(val quality: String) : Intent
        data class LoadSpecificEpisode(val episodeId: Int) : Intent
        data object OnBackPressed : Intent
    }

    data class State(val playerWrapper: PlayerWrapper)

    sealed interface Label {
        data object OnBackPressed : Label
    }
}

@Inject
class VideoStoreFactory(
    private val storeFactory: StoreFactory,
    private val loadPreviousEpisode: LoadPreviousEpisodeUseCase,
    private val loadNextEpisode: LoadNextEpisodeUseCase,
    private val loadSpecificEpisode: LoadSpecificEpisodeUseCase,
    private val getVideoInfo: GetVideoInfoUseCase,
) {

    fun create(): VideoStore =
        object :
            VideoStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "VideoStore",
                initialState = State(PlayerWrapper("")),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class VideoInfoLoaded(val playerWrapper: PlayerWrapper) : Action
    }

    private sealed interface Msg {
        data class VideoInfoLoaded(val playerWrapper: PlayerWrapper) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getVideoInfo().collect {
                    dispatch(Action.VideoInfoLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.LoadNextVideo -> scope.launch { loadNextEpisode() }
                is Intent.LoadPreviousVideo -> scope.launch { loadPreviousEpisode() }
                is Intent.LoadVideoSelectedQuality -> {}
                is Intent.LoadSpecificEpisode -> scope.launch { loadSpecificEpisode(intent.episodeId) }
                is Intent.OnBackPressed -> publish(Label.OnBackPressed)
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.VideoInfoLoaded -> dispatch(Msg.VideoInfoLoaded(action.playerWrapper))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.VideoInfoLoaded -> copy(playerWrapper = msg.playerWrapper)
            }
    }
}
