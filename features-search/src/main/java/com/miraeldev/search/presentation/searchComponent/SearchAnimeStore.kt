package com.miraeldev.search.presentation.searchComponent

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.search.domain.usecases.filterUseCase.ClearAllFiltersUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetFilterListUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.GetSearchHistoryListUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.GetSearchNameUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.GetSearchResultsUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.LoadInitialListUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.SaveNameInAnimeSearchHistoryUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.SearchAnimeByNameUseCase
import com.miraeldev.search.presentation.searchComponent.SearchAnimeStore.Intent
import com.miraeldev.search.presentation.searchComponent.SearchAnimeStore.Label
import com.miraeldev.search.presentation.searchComponent.SearchAnimeStore.State
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchAnimeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class OnSearchChange(val search: String) : Intent
        data class SearchAnimeByName(val name: String) : Intent
        data object ShowSearchHistory : Intent
        data object ShowInitialList : Intent
        data object ClearAllFilters : Intent
        data object OnFilterClicked : Intent
        data class OnAnimeItemClick(val id: Int) : Intent

    }

    data class State(
        val search: String,
        val filterList: ImmutableList<String>,
        val searchHistory: ImmutableList<String>,
        val screenState: SearchAnimeScreenState
    ) {
        sealed class SearchAnimeScreenState {

            data object EmptyList : SearchAnimeScreenState()


            data class InitialList(
                val result: Flow<PagingData<AnimeInfo>>
            ) : SearchAnimeScreenState()

            data object SearchHistory : SearchAnimeScreenState()

            data class SearchResult(
                val result: Flow<PagingData<AnimeInfo>>
            ) : SearchAnimeScreenState()

        }

    }

    sealed interface Label {
        data object OnFilterClicked : Label
        data class OnAnimeItemClick(val id: Int) : Label
    }
}

class SearchAnimeStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val clearAllFilters: ClearAllFiltersUseCase,
    private val loadInitialList: LoadInitialListUseCase,
    private val searchAnimeByName: SearchAnimeByNameUseCase,
    private val saveNameInAnimeSearchHistory: SaveNameInAnimeSearchHistoryUseCase,
    private val getSearchName: GetSearchNameUseCase,
    private val getFilterListUseCase: GetFilterListUseCase,
    private val getSearchHistoryList: GetSearchHistoryListUseCase,
    private val getSearchResults: GetSearchResultsUseCase,
) {

    fun create(): SearchAnimeStore =
        object : SearchAnimeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchAnimeStore",
            initialState = State(
                "",
                persistentListOf(),
                persistentListOf(),
                State.SearchAnimeScreenState.EmptyList
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class SearchNameLoaded(val name: String, val result: Flow<PagingData<AnimeInfo>>) :
            Action

        data class SearchHistoryLoaded(val history: List<String>) : Action
        data class SearchFiltersLoaded(val filters: List<String>) : Action
        data class SearchResultsLoaded(val results: Flow<PagingData<AnimeInfo>>) : Action
        data class InitialListLoaded(val results: Flow<PagingData<AnimeInfo>>) : Action
    }

    private sealed interface Msg {
        data object ShowSearchHistory : Msg
        data class SearchNameLoaded(val name: String, val result: Flow<PagingData<AnimeInfo>>) : Msg
        data class SearchHistoryLoaded(val history: List<String>) : Msg
        data class SearchFiltersLoaded(val filters: List<String>) : Msg
        data class SearchResultsLoaded(val results: Flow<PagingData<AnimeInfo>>) : Msg
        data class InitialListLoaded(val results: Flow<PagingData<AnimeInfo>>) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                searchAnimeByName("")
                getSearchResults().collect {
                    dispatch(Action.InitialListLoaded(it))
                }
            }
            scope.launch {
                getSearchName()
                    .filterNot { it.isEmpty() }
                    .collectLatest {
                        val result = searchAnimeByName(it)
                        dispatch(Action.SearchNameLoaded(it, result))
                        saveNameInAnimeSearchHistory(it)
                    }
            }
            scope.launch {
                getFilterListUseCase().collect {
                    dispatch(Action.SearchFiltersLoaded(it))
                }
            }
            scope.launch {
                getSearchHistoryList().collect {
                    dispatch(Action.SearchHistoryLoaded(it))
                }
            }
            scope.launch {
                getSearchResults().onStart { loadInitialList() }.collect {
                    dispatch(Action.SearchResultsLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.OnSearchChange -> {

                }

                is Intent.SearchAnimeByName -> {
                    scope.launch {
                        searchAnimeByName(intent.name)
                        saveNameInAnimeSearchHistory(intent.name)
                    }
                }

                is Intent.ShowSearchHistory -> dispatch(Msg.ShowSearchHistory)

                is Intent.ShowInitialList -> {
                    scope.launch {
                        clearAllFilters()
                        loadInitialList()
                    }
                }

                is Intent.ClearAllFilters -> scope.launch { clearAllFilters() }

                is Intent.OnFilterClicked -> publish(Label.OnFilterClicked)

                is Intent.OnAnimeItemClick -> publish(Label.OnAnimeItemClick(intent.id))
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.SearchNameLoaded -> dispatch(
                    Msg.SearchNameLoaded(
                        action.name,
                        action.result
                    )
                )

                is Action.SearchHistoryLoaded -> dispatch(Msg.SearchHistoryLoaded(action.history))
                is Action.SearchFiltersLoaded -> dispatch(Msg.SearchFiltersLoaded(action.filters))
                is Action.SearchResultsLoaded -> dispatch(Msg.SearchResultsLoaded(action.results))
                is Action.InitialListLoaded -> dispatch(Msg.InitialListLoaded(action.results))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ShowSearchHistory -> {
                    copy(screenState = State.SearchAnimeScreenState.SearchHistory)
                }

                is Msg.SearchNameLoaded -> {
                    copy(
                        search = msg.name,
                        screenState = State.SearchAnimeScreenState.SearchResult(msg.result)
                    )
                }

                is Msg.SearchHistoryLoaded -> {
                    copy(searchHistory = msg.history.toPersistentList())
                }

                is Msg.SearchFiltersLoaded -> {
                    copy(filterList = msg.filters.toPersistentList())
                }

                is Msg.SearchResultsLoaded -> {
                    copy(screenState = State.SearchAnimeScreenState.SearchResult(msg.results))
                }

                is Msg.InitialListLoaded -> {
                    copy(screenState = State.SearchAnimeScreenState.InitialList(msg.results))
                }
            }
    }
}
