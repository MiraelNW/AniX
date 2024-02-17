package com.miraeldev.animelist.presentation.home.homeComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.animelist.domain.useCases.AddAnimeToListUseCase
import com.miraeldev.animelist.domain.useCases.GetUserInfoUseCase
import com.miraeldev.animelist.domain.useCases.LoadVideoIdUseCase
import com.miraeldev.animelist.domain.useCases.homeScreenUseCases.LoadDataUseCase
import com.miraeldev.animelist.presentation.home.homeComponent.HomeStore.Intent
import com.miraeldev.animelist.presentation.home.homeComponent.HomeStore.Label
import com.miraeldev.animelist.presentation.home.homeComponent.HomeStore.State
import com.miraeldev.user.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class LoadAnimeVideo(val anime: LastWatchedAnime) : Intent
        data class AddAnimeToList(
            val user: User,
            val isSelected: Boolean,
            val animeItem: LastWatchedAnime
        ) : Intent

        data class OnAnimeItemClick(val id: Int) : Intent
        data class OnSeeAllClick(val id: Int) : Intent
        data class OnPlayClick(val id: Int) : Intent
    }


    data class State(val screenState: HomeScreenState) {
        sealed interface HomeScreenState {
            data object Loading : HomeScreenState
            data class HomeLoaded(
                val newAnimeList: List<AnimeInfo> = emptyList(),
                val popularAnimeList: List<AnimeInfo> = emptyList(),
                val nameAnimeList: List<AnimeInfo> = emptyList(),
                val filmsAnimeList: List<AnimeInfo> = emptyList(),
                val user: User = User()
            ) : HomeScreenState

            data object Initial : HomeScreenState
        }
    }


    sealed interface Label {
        data class OnAnimeItemClick(val id: Int) : Label
        data class OnSeeAllClick(val id: Int) : Label
        data class OnPlayClick(val id: Int) : Label
    }
}

class HomeStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val loadDataUseCase: LoadDataUseCase,
    private val addAnimeToListUseCase: AddAnimeToListUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val loadVideoIdUseCase: LoadVideoIdUseCase
) {

    fun create(): HomeStore =
        object : HomeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = State(screenState = State.HomeScreenState.Initial),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class AnimeListForCategory(val animeMap: Map<Int, List<AnimeInfo>>, val user: User) :
            Action

        data object Loading : Action
    }

    private sealed interface Msg {
        data class AnimeListForCategory(val animeMap: Map<Int, List<AnimeInfo>>, val user: User) :
            Msg

        data object Loading : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                val map = loadDataUseCase()
                getUserInfoUseCase().collectLatest { user ->
                    dispatch(Action.AnimeListForCategory(map, user))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.LoadAnimeVideo -> {
                    scope.launch {
                        loadVideoIdUseCase(intent.anime)
                    }
                }

                is Intent.AddAnimeToList -> {
                    scope.launch {
                        addAnimeToListUseCase(intent.isSelected, intent.animeItem)
                    }
                }

                is Intent.OnAnimeItemClick -> publish(Label.OnAnimeItemClick(intent.id))

                is Intent.OnSeeAllClick -> publish(Label.OnSeeAllClick(intent.id))

                is Intent.OnPlayClick -> publish(Label.OnPlayClick(intent.id))
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.AnimeListForCategory ->
                    dispatch(Msg.AnimeListForCategory(action.animeMap, action.user))

                is Action.Loading -> dispatch(Msg.Loading)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.AnimeListForCategory -> {
                val newAnimeList = msg.animeMap[0] ?: emptyList()
                val popularAnimeList = msg.animeMap[1] ?: emptyList()
                val nameAnimeList = msg.animeMap[2] ?: emptyList()
                val filmsAnimeList = msg.animeMap[3] ?: emptyList()
                copy(
                    screenState = State.HomeScreenState.HomeLoaded(
                        newAnimeList = newAnimeList,
                        popularAnimeList = popularAnimeList,
                        nameAnimeList = nameAnimeList,
                        filmsAnimeList = filmsAnimeList,
                        user = msg.user
                    )
                )
            }

            is Msg.Loading -> copy(screenState = State.HomeScreenState.Loading)
        }
    }
}
