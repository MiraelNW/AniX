package com.miraeldev.search.presentation.searchComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnAnimeItemClick
import com.miraeldev.models.OnFilterClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultSearchAnimeComponentFactory = (ComponentContext, OnAnimeItemClick, OnFilterClick) -> DefaultSearchAnimeComponent

@Inject
class DefaultSearchAnimeComponent(
    private val storeFactory: SearchAnimeStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onAnimeItemClick: (Int) -> Unit,
    @Assisted onFilterClick: () -> Unit
) : SearchAnimeComponent, ComponentContext by componentContext {

    private val store: SearchAnimeStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchAnimeStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SearchAnimeStore.Label.OnFilterClicked -> onFilterClick()
                    is SearchAnimeStore.Label.OnAnimeItemClick -> onAnimeItemClick(it.id)
                }
            }
        }
    }

    override fun onSearchChange(search: String) {
        store.accept(SearchAnimeStore.Intent.OnSearchChange(search))
    }

    override fun searchAnimeByName(name: String) {
        store.accept(SearchAnimeStore.Intent.SearchAnimeByName(name))
    }

    override fun showSearchHistory() {
        store.accept(SearchAnimeStore.Intent.ShowSearchHistory)
    }

    override fun showInitialList() {
        store.accept(SearchAnimeStore.Intent.ShowInitialList)
    }

    override fun clearAllFilters() {
        store.accept(SearchAnimeStore.Intent.ClearAllFilters)
    }

    override fun onFilterClicked() {
        store.accept(SearchAnimeStore.Intent.OnFilterClicked)
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(SearchAnimeStore.Intent.OnAnimeItemClick(id))
    }
}