package com.miraelDev.hikari.presentation.searchAimeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miraelDev.hikari.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepositoryUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchHistoryListUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchNameUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchResultsUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.LoadInitialListUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.SaveNameInAnimeSearchHistoryUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.ClearAllFiltersInSearchRepositoryUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.GetFilterListUseCase
import com.miraelDev.hikari.exntensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

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
//        .onStart {
//            when ( val res = savedStateHandle[STATE_CODE] ?: 0) {
//                1 -> {
//                    Log.d("tag","1")
//                    Log.d("tag",(savedStateHandle[STATE_CODE] ?: 0).toString())
//                    showSearchHistory()
//                }
//
//                2 -> {
//                    Log.d("tag","2")
//                    Log.d("tag",(savedStateHandle[STATE_CODE] ?: 0) .toString())
//                    searchAnimeByName(savedStateHandle[SEARCH_NAME] ?: "")
//                }
//
//                else -> {
//                    Log.d("tag","0")
//                    Log.d("tag",(savedStateHandle[STATE_CODE] ?: 0 ).toString())
//                    loadInitialList()
//                }
//            }
//        }
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
                    savedStateHandle[SEARCH_NAME] = name
                    savedStateHandle[STATE_CODE] = 2
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
            savedStateHandle[STATE_CODE] = 2
            savedStateHandle[SEARCH_NAME] = name
            Log.d("tag",(savedStateHandle[STATE_CODE] ?: 0) .toString())
            saveNameInAnimeSearchHistoryUseCase(name)
        }
    }

    fun showSearchHistory() {
        viewModelScope.launch {
            showSearchHistoryFlow.emit(SearchAnimeScreenState.SearchHistory)
            savedStateHandle[STATE_CODE] = 1
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

    override fun onCleared() {
        super.onCleared()
        savedStateHandle[STATE_CODE] = null
        savedStateHandle[SEARCH_NAME] = null
    }

    companion object {
        private const val STATE_CODE = "state_code"
        private const val SEARCH_NAME = "search_name"
    }
}