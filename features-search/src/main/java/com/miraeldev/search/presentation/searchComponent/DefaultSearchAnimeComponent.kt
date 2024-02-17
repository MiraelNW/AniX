package com.miraeldev.search.presentation.searchComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSearchAnimeComponent @AssistedInject constructor(
    private val storeFactory: SearchAnimeStoreFactory,
    @Assisted("onFilterClicked") onFilterClicked: () -> Unit,
    @Assisted("onAnimeItemClick") onAnimeItemClick: (Int) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : SearchAnimeComponent, ComponentContext by componentContext {

    private val store: SearchAnimeStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchAnimeStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is SearchAnimeStore.Label.OnFilterClicked -> onFilterClicked()
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

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onFilterClicked") onFilterClicked: () -> Unit,
            @Assisted("onAnimeItemClick") onAnimeItemClick: (Int) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultSearchAnimeComponent
    }
}