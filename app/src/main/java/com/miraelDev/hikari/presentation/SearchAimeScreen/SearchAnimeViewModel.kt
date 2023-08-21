package com.miraelDev.hikari.presentation.SearchAimeScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepository
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchAnimeResultsUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchHistoryListUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.GetSearchNameUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.SaveNameInAnimeSearchHistoryUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.GetFilterListUseCase
import com.miraelDev.hikari.exntensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val getFilterListUseCase: GetFilterListUseCase,

    private val clearAllFiltersInSearchRepository: ClearAllFiltersInSearchRepository,
    private val clearAllFiltersInFilterRepository: ClearAllFiltersInFilterRepository,

    private val searchAnimeByNameUseCase: SearchAnimeByNameUseCase,
    private val getSearchAnimeResultsUseCase: GetSearchAnimeResultsUseCase,

    private val saveNameInAnimeSearchHistoryUseCase: SaveNameInAnimeSearchHistoryUseCase,
    private val getSearchHistoryListUseCase: GetSearchHistoryListUseCase,

    private val getSearchNameUseCase: GetSearchNameUseCase
) : ViewModel() {


    val filterList = getFilterListUseCase()

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    private val startLoad = MutableSharedFlow<SearchAnimeScreenState>()

    private val showStartAnimationFlow = MutableSharedFlow<SearchAnimeScreenState>()

    private val showSearchHistoryFlow = MutableSharedFlow<SearchAnimeScreenState>()

    val searchHistory = getSearchHistoryListUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            listOf()
        )

    private val searchResultsFlow = getSearchAnimeResultsUseCase()
        .map {

            when (val res = it) {
                is Result.Failure -> {
                    SearchAnimeScreenState.SearchFailure(failure = res.failureCause) as SearchAnimeScreenState
                }

                is Result.Success -> {
                    SearchAnimeScreenState.SearchResult(result = res.animeList) as SearchAnimeScreenState
                }

                is Result.Initial -> {
                    SearchAnimeScreenState.Loading as SearchAnimeScreenState
                }

            }

        }
        .onStart { SearchAnimeScreenState.Initial }
        .mergeWith(showStartAnimationFlow)
        .mergeWith(showSearchHistoryFlow)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SearchAnimeScreenState.Initial
        )


    val animeBySearch = searchResultsFlow
        .mergeWith(startLoad)

    init {
        getSearchName()
    }

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    private fun getSearchName() {
        viewModelScope.launch {
            getSearchNameUseCase().collect { name ->
                updateSearchTextState(name)
            }
        }
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {
            startLoad.emit(SearchAnimeScreenState.Loading)
            saveNameInAnimeSearchHistoryUseCase(name)
            searchAnimeByNameUseCase(name)
        }
    }

    fun showStartAnimation() {
        viewModelScope.launch {
            showStartAnimationFlow.emit(SearchAnimeScreenState.Initial)
        }
    }

    fun showSearchHistory() {
        viewModelScope.launch {
            showSearchHistoryFlow.emit(SearchAnimeScreenState.SearchHistory)
        }
    }

    fun clearAllFilters() {
        viewModelScope.launch {
            clearAllFiltersInFilterRepository()
            clearAllFiltersInSearchRepository()
        }
    }
}