package com.miraeldev.search.presentation.searchHistoryScreen.searchHistoryComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnFilterClick
import com.miraeldev.models.SearchAnimeByName
import com.miraeldev.models.ShowInitialList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultSearchHistoryComponentFactory =
    (ComponentContext, OnFilterClick, ShowInitialList, SearchAnimeByName) -> DefaultSearchHistoryComponent
@Inject
class DefaultSearchHistoryComponent(
    private val storeFactory: SearchHistoryStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onFilterClick: () -> Unit,
    @Assisted showInitialList: () -> Unit,
    @Assisted searchAnimeByName: (String) -> Unit,
) : SearchHistoryComponent, ComponentContext by componentContext {

    private val store: SearchHistoryStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchHistoryStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SearchHistoryStore.Label.OnFilterClick -> onFilterClick()
                    is SearchHistoryStore.Label.ShowInitialList -> showInitialList()
                    is SearchHistoryStore.Label.SearchAnimeByName -> searchAnimeByName(it.animeName)
                }
            }
        }
    }

    override fun searchAnimeByName(name: String) {
        store.accept(SearchHistoryStore.Intent.SearchAnimeByName(name))
    }

    override fun showInitialList() {
        store.accept(SearchHistoryStore.Intent.ShowInitialList)
    }

    override fun onFilterClicked() {
        store.accept(SearchHistoryStore.Intent.OnFilterClick)
    }

    override fun onSearchHistoryItemClick(search: String) {
        store.accept(SearchHistoryStore.Intent.OnSearchHistoryItemClick(search))
    }

    override fun onSearchChange(search: String) {
        store.accept(SearchHistoryStore.Intent.SearchTextChanged(search))
    }
}