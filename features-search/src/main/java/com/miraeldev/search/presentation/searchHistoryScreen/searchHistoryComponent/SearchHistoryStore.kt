@file:Suppress("MaxLineLength")
package com.miraeldev.search.presentation.searchHistoryScreen.searchHistoryComponent

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.miraeldev.search.domain.usecases.filterUseCase.GetFilterListUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.GetSearchHistoryListUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.GetSearchNameUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.SaveNameInAnimeSearchHistoryUseCase
import com.miraeldev.search.domain.usecases.searchUseCase.SaveSearchTextUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

interface SearchHistoryStore :
    Store<SearchHistoryStore.Intent, SearchHistoryStore.State, SearchHistoryStore.Label> {

    sealed interface Intent {
        data class SearchAnimeByName(val animeName: String) : Intent
        data class OnSearchHistoryItemClick(val animeName: String) : Intent
        data class SearchTextChanged(val searchText: String) : Intent
        data object ShowInitialList : Intent
        data object OnFilterClick : Intent
    }

    data class State(
        val search: String,
        val filterList: ImmutableList<String>,
        val historyList: ImmutableList<String>
    )

    sealed interface Label {
        data object OnFilterClick : Label
        data object ShowInitialList : Label
        data class SearchAnimeByName(val animeName: String) : Label
    }
}

@Inject
class SearchHistoryStoreFactory(
    private val storeFactory: StoreFactory,
    private val getSearchHistoryList: GetSearchHistoryListUseCase,
    private val saveSearchTextUseCase: SaveSearchTextUseCase,
    private val saveNameInAnimeSearchHistoryUseCase: SaveNameInAnimeSearchHistoryUseCase,
    private val getFilterListUseCase: GetFilterListUseCase,
    private val getSearchNameUseCase: GetSearchNameUseCase
) {

    fun create(): SearchHistoryStore =
        object :
            SearchHistoryStore,
            Store<SearchHistoryStore.Intent, SearchHistoryStore.State, SearchHistoryStore.Label> by storeFactory.create(
                name = "SearchHistoryStore",
                initialState = SearchHistoryStore.State(
                    "",
                    persistentListOf(),
                    persistentListOf()
                ),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class SearchNameLoaded(val name: String) : Action
        data class SearchFiltersLoaded(val filters: List<String>) : Action
        data class SearchHistoryLoaded(val history: List<String>) : Action
    }

    private sealed interface Msg {
        data class SearchNameLoaded(val name: String) : Msg
        data class SearchFiltersLoaded(val filters: List<String>) : Msg
        data class SearchHistoryLoaded(val history: List<String>) : Msg
        data class SearchTextChanged(val search: String) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
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
                getSearchNameUseCase()
                    .collect {
                        dispatch(Action.SearchNameLoaded(it))
                    }
            }
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<SearchHistoryStore.Intent, Action, SearchHistoryStore.State, Msg, SearchHistoryStore.Label>() {
        override fun executeIntent(
            intent: SearchHistoryStore.Intent,
            getState: () -> SearchHistoryStore.State
        ) {
            when (intent) {

                is SearchHistoryStore.Intent.ShowInitialList -> publish(SearchHistoryStore.Label.ShowInitialList)
                is SearchHistoryStore.Intent.OnFilterClick -> publish(SearchHistoryStore.Label.OnFilterClick)
                is SearchHistoryStore.Intent.SearchAnimeByName -> {
                    scope.launch {
                        saveNameInAnimeSearchHistoryUseCase(intent.animeName)
                        saveSearchTextUseCase(intent.animeName)
                        publish(SearchHistoryStore.Label.SearchAnimeByName(intent.animeName))
                    }
                }

                is SearchHistoryStore.Intent.SearchTextChanged ->
                    dispatch(Msg.SearchTextChanged(intent.searchText))

                is SearchHistoryStore.Intent.OnSearchHistoryItemClick -> {
                    saveSearchTextUseCase(intent.animeName)
                    publish(SearchHistoryStore.Label.SearchAnimeByName(intent.animeName))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> SearchHistoryStore.State) {
            when (action) {
                is Action.SearchNameLoaded -> dispatch(Msg.SearchNameLoaded(action.name))
                is Action.SearchFiltersLoaded -> dispatch(Msg.SearchFiltersLoaded(action.filters))
                is Action.SearchHistoryLoaded -> dispatch(Msg.SearchHistoryLoaded(action.history))
            }
        }
    }

    private object ReducerImpl : Reducer<SearchHistoryStore.State, Msg> {
        override fun SearchHistoryStore.State.reduce(msg: Msg): SearchHistoryStore.State =
            when (msg) {
                is Msg.SearchNameLoaded -> copy(search = msg.name)

                is Msg.SearchFiltersLoaded -> copy(filterList = msg.filters.toPersistentList())

                is Msg.SearchHistoryLoaded -> copy(historyList = msg.history.toPersistentList())
                is Msg.SearchTextChanged -> copy(search = msg.search)
            }
    }
}
