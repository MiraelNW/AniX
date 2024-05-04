package com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent

import kotlinx.coroutines.flow.StateFlow

interface InitialSearchComponent {

    val model: StateFlow<InitialSearchStore.State>
    fun onAnimeItemClick(id: Int)
    fun showSearchHistory()
    fun loadNextPage()
}