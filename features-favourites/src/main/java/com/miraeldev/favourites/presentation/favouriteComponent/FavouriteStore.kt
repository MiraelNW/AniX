package com.miraeldev.favourites.presentation.favouriteComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.favourites.domain.useCases.GetFavouriteAnimeListUseCase
import com.miraeldev.favourites.domain.useCases.LoadAnimeListUseCase
import com.miraeldev.favourites.domain.useCases.SaveSearchTextUseCase
import com.miraeldev.favourites.domain.useCases.SearchAnimeItemInDatabaseUseCase
import com.miraeldev.favourites.domain.useCases.SelectAnimeItemUseCase
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteStore.Intent
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteStore.Label
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteStore.State
import com.miraeldev.result.FailureCauses
import com.miraeldev.result.ResultAnimeInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface FavouriteStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnAnimeItemClick(val id: Int) : Intent
        data class NavigateToSearchScreen(val search: String) : Intent
        data class UpdateSearchTextState(val search: String) : Intent
        data class SelectAnimeItem(val animeInfo: AnimeInfo) : Intent
        data class SearchAnimeItemInDatabase(val name: String) : Intent
        data class SearchAnimeByName(val name: String) : Intent
        data object LoadAnimeList : Intent
    }

    data class State(val search: String, val screenState: FavouriteListScreenState) {
        sealed interface FavouriteListScreenState {
            data object Loading : FavouriteListScreenState
            data object Initial : FavouriteListScreenState
            data class Failure(val failure: FailureCauses) : FavouriteListScreenState
            data class Result(val result: ImmutableList<AnimeInfo>) : FavouriteListScreenState
        }
    }

    sealed interface Label {
        data class OnAnimeItemClicked(val id: Int) : Label
        data class NavigateToSearchScreen(val search: String) : Label
    }
}

@Inject
class FavouriteStoreFactory(
    private val storeFactory: StoreFactory,
    private val getFavouriteAnimeListUseCase: GetFavouriteAnimeListUseCase,
    private val selectAnimeItemUseCase: SelectAnimeItemUseCase,
    private val searchAnimeItemInDatabaseUseCase: SearchAnimeItemInDatabaseUseCase,
    private val loadAnimeListUseCase: LoadAnimeListUseCase,
    private val saveSearchTextUseCase: SaveSearchTextUseCase,
) {

    fun create(): FavouriteStore =
        object : FavouriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = State("", State.FavouriteListScreenState.Initial),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object FavouriteListLoading : Action
        data class FavouriteList(val list: ResultAnimeInfo) : Action
    }

    private sealed interface Msg {
        data class UpdateSearchTextState(val search: String) : Msg
        data object FavouriteListLoading : Msg
        data class FavouriteList(val result: ResultAnimeInfo) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.FavouriteListLoading)
                getFavouriteAnimeListUseCase().collect {
                    dispatch(Action.FavouriteList(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnAnimeItemClick -> publish(Label.OnAnimeItemClicked(intent.id))

                is Intent.NavigateToSearchScreen -> publish(Label.NavigateToSearchScreen(intent.search))

                is Intent.UpdateSearchTextState -> dispatch(Msg.UpdateSearchTextState(intent.search))

                is Intent.SelectAnimeItem -> {
                    scope.launch {
                        selectAnimeItemUseCase(false, intent.animeInfo)
                    }
                }

                is Intent.SearchAnimeItemInDatabase -> scope.launch {
                    searchAnimeItemInDatabaseUseCase(
                        intent.name
                    )
                }


                is Intent.SearchAnimeByName -> scope.launch { saveSearchTextUseCase(intent.name) }

                is Intent.LoadAnimeList -> scope.launch { loadAnimeListUseCase() }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteListLoading -> {
                    dispatch(Msg.FavouriteListLoading)
                }

                is Action.FavouriteList -> {
                    dispatch(Msg.FavouriteList(action.list))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.UpdateSearchTextState -> copy(search = msg.search)

                is Msg.FavouriteListLoading -> copy(screenState = State.FavouriteListScreenState.Loading)

                is Msg.FavouriteList -> {
                    val state = when (msg.result) {
                        is ResultAnimeInfo.Failure -> {
                            State.FavouriteListScreenState.Failure(msg.result.failureCause)
                        }

                        is ResultAnimeInfo.Success -> {
                            State.FavouriteListScreenState.Result(msg.result.animeList.toImmutableList())
                        }

                        is ResultAnimeInfo.Initial -> {
                            State.FavouriteListScreenState.Initial
                        }
                    }
                    copy(screenState = state)
                }
            }
    }
}
