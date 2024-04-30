package com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingState
import com.miraeldev.search.domain.usecases.searchUseCase.GetSearchInitialListUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.LoadNextPageUseCase
import com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent.InitialSearchStore.Intent
import com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent.InitialSearchStore.Label
import com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent.InitialSearchStore.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface InitialSearchStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnAnimeItemClick(val id: Int) : Intent
        data object ShowSearchHistory : Intent
        data object LoadNextPage : Intent
    }

    data class State(
        val search: String,
        val filterList: ImmutableList<String>,
        val initialList: PagingState,
    )

    sealed interface Label {
        data class OnAnimeItemClick(val id: Int) : Label
        data object ShowSearchHistory : Label
    }
}

@Inject
class InitialSearchStoreFactory(
    private val storeFactory: StoreFactory,
    private val getSearchInitialList: GetSearchInitialListUseCase,
    private val loadNextPageUseCase: LoadNextPageUseCase
) {

    fun create(): InitialSearchStore =
        object :
            InitialSearchStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "SearchAnimeStore",
                initialState = State(
                    "",
                    persistentListOf(),
                    PagingState(emptyList(), LoadState.EMPTY)
                ),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class InitialListLoaded(val pagingState: PagingState) : Action
    }

    private sealed interface Msg {
        data class InitialListLoaded(val pagingState: PagingState) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                loadNextPageUseCase()
                getSearchInitialList().collect { pagingState ->
                    dispatch(Action.InitialListLoaded(pagingState))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnAnimeItemClick -> publish(Label.OnAnimeItemClick(intent.id))
                is Intent.ShowSearchHistory -> publish(Label.ShowSearchHistory)
                is Intent.LoadNextPage -> {
                    scope.launch {
                        loadNextPageUseCase()
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.InitialListLoaded -> dispatch(Msg.InitialListLoaded(action.pagingState))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.InitialListLoaded -> {
                    copy(initialList = msg.pagingState)
                }
            }
    }
}
