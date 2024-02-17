package com.miraeldev.search.presentation.searchComponent

import kotlinx.coroutines.flow.StateFlow

interface SearchAnimeComponent {

    val model: StateFlow<SearchAnimeStore.State>

    fun onSearchChange(search: String)
    fun searchAnimeByName(name: String)
    fun showSearchHistory()
    fun showInitialList()
    fun clearAllFilters()
    fun onFilterClicked()
    fun onAnimeItemClick(id: Int)
}