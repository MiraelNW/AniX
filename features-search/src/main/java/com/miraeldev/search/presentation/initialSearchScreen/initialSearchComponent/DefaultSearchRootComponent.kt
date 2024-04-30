package com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.OnAnimeItemClick
import com.miraeldev.models.ShowSearchHistory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultInitialSearchComponentFactory = (ComponentContext, OnAnimeItemClick, ShowSearchHistory) ->
DefaultInitialSearchComponent

@Inject
class DefaultInitialSearchComponent(
    private val storeFactory: InitialSearchStoreFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted onAnimeItemClick: (Int) -> Unit,
    @Assisted showSearchHistory: () -> Unit,
) : InitialSearchComponent, ComponentContext by componentContext {

    private val store: InitialSearchStore = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<InitialSearchStore.State> = store.stateFlow

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is InitialSearchStore.Label.OnAnimeItemClick -> onAnimeItemClick(it.id)
                    is InitialSearchStore.Label.ShowSearchHistory -> showSearchHistory()
                }
            }
        }
    }

    override fun onAnimeItemClick(id: Int) {
        store.accept(InitialSearchStore.Intent.OnAnimeItemClick(id))
    }

    override fun showSearchHistory() {
        store.accept(InitialSearchStore.Intent.ShowSearchHistory)
    }

    override fun loadNextPage() {
        store.accept(InitialSearchStore.Intent.LoadNextPage)
    }
}