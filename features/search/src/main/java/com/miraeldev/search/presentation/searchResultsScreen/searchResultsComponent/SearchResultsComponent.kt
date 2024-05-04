package com.miraeldev.search.presentation.searchResultsScreen.searchResultsComponent

import kotlinx.coroutines.flow.StateFlow

interface SearchResultsComponent {

    val model: StateFlow<SearchResultsStore.State>
    fun onAnimeItemClick(id: Int)
    fun showSearchHistory(search: String)
    fun onFilterClicked()
}