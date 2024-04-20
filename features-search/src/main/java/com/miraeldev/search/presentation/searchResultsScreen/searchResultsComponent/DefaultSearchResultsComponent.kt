package com.miraeldev.search.presentation.searchResultsScreen.searchResultsComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnAnimeItemClick
import com.miraeldev.models.OnShowSearchHistory
import com.miraeldev.models.OnFilterClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultSearchResultsComponentFactory = (ComponentContext, OnAnimeItemClick, OnFilterClick, OnShowSearchHistory) -> DefaultSearchResultsComponent

@Inject
class DefaultSearchResultsComponent(
    private val storeFactory: SearchResultsStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onAnimeItemClick: (Int) -> Unit,
    @Assisted onFilterClick: () -> Unit,
    @Assisted onShowSearchHistory: () -> Unit,
) : SearchResultsComponent, ComponentContext by componentContext {

    private val store: SearchResultsStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchResultsStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SearchResultsStore.Label.OnAnimeItemClick -> onAnimeItemClick(it.id)
                    is SearchResultsStore.Label.OnFilterClick -> onFilterClick()
                    is SearchResultsStore.Label.OnShowSearchHistory -> onShowSearchHistory()
                }
            }
        }
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(SearchResultsStore.Intent.OnAnimeItemClick(id))
    }

    override fun showSearchHistory(search: String) {
        store.accept(SearchResultsStore.Intent.ShowSearchHistory(search))
    }

    override fun onFilterClicked() {
        store.accept(SearchResultsStore.Intent.OnFilterClick)
    }
}