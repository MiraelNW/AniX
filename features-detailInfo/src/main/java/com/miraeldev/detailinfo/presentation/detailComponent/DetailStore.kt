package com.miraeldev.detailinfo.presentation.detailComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.domain.useCases.DownloadAnimeEpisodeUseCase
import com.miraeldev.detailinfo.domain.useCases.GetAnimeDetailUseCase
import com.miraeldev.detailinfo.domain.useCases.LoadAnimeDetailUseCase
import com.miraeldev.detailinfo.domain.useCases.LoadVideoIdUseCase
import com.miraeldev.detailinfo.domain.useCases.SelectAnimeItemUseCase
import com.miraeldev.detailinfo.presentation.detailComponent.DetailStore.Intent
import com.miraeldev.detailinfo.presentation.detailComponent.DetailStore.Label
import com.miraeldev.detailinfo.presentation.detailComponent.DetailStore.State
import com.miraeldev.imageloader.VaumaImageLoader
import com.miraeldev.result.FailureCauses
import com.miraeldev.models.result.ResultAnimeDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface DetailStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnBackClicked : Intent
        data class LoadAnimeDetail(val id: Int) : Intent
        data class DownloadEpisode(val url: String, val videoName: String) : Intent
        data class LoadAnimeVideo(val animeItem: AnimeDetailInfo, val videoId: Int) : Intent
        data class OnAnimeItemClick(val id: Int) : Intent
        data object OnSeriesClick : Intent
        data class SelectAnimeItem(val isSelected: Boolean, val animeInfo: AnimeDetailInfo) : Intent
    }

    data class State(val animeDetailScreenState: AnimeDetailScreenState) {
        sealed interface AnimeDetailScreenState {
            data object Loading : AnimeDetailScreenState

            data object Initial : AnimeDetailScreenState

            data class SearchFailure(val failure: FailureCauses) : AnimeDetailScreenState

            data class SearchResult(val result: ImmutableList<AnimeDetailInfo>) :
                AnimeDetailScreenState
        }
    }

    sealed interface Label {
        data object OnBackClicked : Label
        data object OnSeriesClick : Label
        data class OnAnimeItemClick(val id: Int) : Label
    }
}

@Inject
class DetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val selectAnimeItemUseCase: SelectAnimeItemUseCase,
    private val loadVideoIdUseCase: LoadVideoIdUseCase,
    private val loadAnimeDetailUseCase: LoadAnimeDetailUseCase,
    private val downloadAnimeEpisodeUseCase: DownloadAnimeEpisodeUseCase,
) {

    fun create(): DetailStore =
        object : DetailStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailStore",
            initialState = State(State.AnimeDetailScreenState.Initial),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class AnimeDetailLoaded(val state: State.AnimeDetailScreenState) : Action
        data object AnimeDetailLoading : Action
    }

    private sealed interface Msg {
        data class AnimeDetailLoaded(val state: State.AnimeDetailScreenState) : Msg
        data object AnimeDetailLoading : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.AnimeDetailLoading)
                getAnimeDetailUseCase().map {
                    when (val res = it) {
                        is ResultAnimeDetail.Success -> {
                            State.AnimeDetailScreenState.SearchResult(result = res.animeList.toImmutableList()) as State.AnimeDetailScreenState
                        }

                        is ResultAnimeDetail.Failure -> {
                            State.AnimeDetailScreenState.SearchFailure(failure = res.failureCause) as State.AnimeDetailScreenState
                        }

                        is ResultAnimeDetail.Initial -> {
                            State.AnimeDetailScreenState.Loading as State.AnimeDetailScreenState
                        }

                    }
                }.collect {
                    dispatch(Action.AnimeDetailLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnBackClicked -> {
                    publish(Label.OnBackClicked)
                }

                is Intent.LoadAnimeDetail -> {
                    scope.launch {
                        loadAnimeDetailUseCase(intent.id)
                    }
                }

                is Intent.DownloadEpisode -> {
                    scope.launch {
                        downloadAnimeEpisodeUseCase(intent.url, intent.videoName)
                    }
                }

                is Intent.LoadAnimeVideo -> {
                    scope.launch {
                        loadVideoIdUseCase(intent.animeItem, intent.videoId)
                    }
                }

                is Intent.OnAnimeItemClick -> {
                    publish(Label.OnAnimeItemClick(intent.id))
                }

                is Intent.OnSeriesClick -> {
                    publish(Label.OnSeriesClick)
                }

                is Intent.SelectAnimeItem -> {
                    scope.launch {
                        selectAnimeItemUseCase(intent.isSelected, intent.animeInfo)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.AnimeDetailLoading -> {
                    dispatch(Msg.AnimeDetailLoading)
                }
                is Action.AnimeDetailLoaded -> {
                    dispatch(Msg.AnimeDetailLoaded(action.state))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.AnimeDetailLoading -> {
                    copy(animeDetailScreenState = State.AnimeDetailScreenState.Loading)
                }
                is Msg.AnimeDetailLoaded -> {
                    copy(animeDetailScreenState = msg.state)
                }
            }
    }
}
