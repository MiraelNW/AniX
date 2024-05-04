package com.miraeldev.search.presentation.searchHistoryScreen.searchHistoryComponent

import kotlinx.coroutines.flow.StateFlow

interface SearchHistoryComponent {

    val model: StateFlow<SearchHistoryStore.State>
    fun onSearchHistoryItemClick(search: String)
    fun onSearchChange(search: String)
    fun searchAnimeByName(name: String)
    fun showInitialList()
    fun onFilterClicked()
}