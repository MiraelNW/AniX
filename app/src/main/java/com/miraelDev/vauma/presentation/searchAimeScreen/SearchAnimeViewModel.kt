package com.miraelDev.vauma.presentation.searchAimeScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraelDev.vauma.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepositoryUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.GetSearchHistoryListUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.GetSearchNameUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.GetSearchResultsUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.LoadInitialListUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.SaveNameInAnimeSearchHistoryUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase.ClearAllFiltersInSearchRepositoryUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase.GetFilterListUseCase
import com.miraelDev.vauma.exntensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val getFilterListUseCase: GetFilterListUseCase,

    private val searchAnimeByNameUseCase: SearchAnimeByNameUseCase,

    private val getSearchResults: GetSearchResultsUseCase,
    private val loadInitialList: LoadInitialListUseCase,

    private val clearAllFiltersInSearchRepository: ClearAllFiltersInSearchRepositoryUseCase,
    private val clearAllFiltersInFilterRepository: ClearAllFiltersInFilterRepositoryUseCase,

    private val saveNameInAnimeSearchHistoryUseCase: SaveNameInAnimeSearchHistoryUseCase,

    private val getSearchHistoryListUseCase: GetSearchHistoryListUseCase,

    private val getSearchNameUseCase: GetSearchNameUseCase,
) : ViewModel() {

    val searchTextState = mutableStateOf("")

    private val searchResult = MutableSharedFlow<SearchAnimeScreenState>(replay = 1)

    private val showSearchHistoryFlow = MutableSharedFlow<SearchAnimeScreenState>()

    private val initialState = getSearchResults()
        .map {
            SearchAnimeScreenState.InitialList(result = it) as SearchAnimeScreenState
        }

    val filterList = getFilterListUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    val searchHistory = getSearchHistoryListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            listOf()
        )

    val screenState = searchResult
        .onStart { loadInitialList() }
        .mergeWith(showSearchHistoryFlow)
        .mergeWith(initialState)
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            SearchAnimeScreenState.EmptyList
        )

    init {
        getSearchName()
    }

    fun updateSearchTextState(value: String) {
        searchTextState.value = value
    }

    private fun getSearchName() {
        viewModelScope.launch {
            getSearchNameUseCase()
                .filter { it.isNotEmpty() }
                .collectLatest { name ->
                    searchResult.emit(
                        SearchAnimeScreenState.SearchResult(
                            result = searchAnimeByNameUseCase(name).cachedIn(viewModelScope)
                        )
                    )
                    updateSearchTextState(name)
                }
        }
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {
            searchResult.emit(
                SearchAnimeScreenState.SearchResult(
                    result = searchAnimeByNameUseCase(name).cachedIn(viewModelScope)
                )
            )
            saveNameInAnimeSearchHistoryUseCase(name)
        }
    }

    fun showSearchHistory() {
        viewModelScope.launch {
            showSearchHistoryFlow.emit(SearchAnimeScreenState.SearchHistory)
        }
    }

    fun showInitialList() {
        clearAllFilters()
        viewModelScope.launch {
            loadInitialList()
        }
    }

    fun clearAllFilters() {
        viewModelScope.launch {
            clearAllFiltersInFilterRepository()
            clearAllFiltersInSearchRepository()
        }
    }
}