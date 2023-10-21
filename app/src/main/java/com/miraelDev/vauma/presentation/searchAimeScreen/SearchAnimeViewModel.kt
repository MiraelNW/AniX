package com.miraelDev.vauma.presentation.searchAimeScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.common.collect.ImmutableList
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
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.immutableListOf
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

    private val _searchTextState = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val searchResult = MutableSharedFlow<SearchAnimeScreenState>(replay = 1)

    private val showSearchHistoryFlow = MutableSharedFlow<SearchAnimeScreenState>()

    private val initialState = getSearchResults()
        .map {
            val result = it.cachedIn(viewModelScope)
            SearchAnimeScreenState.InitialList(result = result) as SearchAnimeScreenState
        }

    val filterList = getFilterListUseCase()
        .onEach { searchAnimeByName(searchTextState.value) }
        .stateIn(viewModelScope, SharingStarted.Lazily, immutableListOf())

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
        _searchTextState.value = value
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