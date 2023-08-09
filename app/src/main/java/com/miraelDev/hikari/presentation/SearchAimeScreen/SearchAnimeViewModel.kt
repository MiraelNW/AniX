package com.miraelDev.hikari.presentation.SearchAimeScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.domain.usecases.SearchUsecase.filterUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.hikari.domain.usecases.SearchUsecase.filterUsecase.GetFilterListUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.GetSearchAnimeResultsUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.GetSearchHistoryListUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SaveNameInAnimeSearchHistoryUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
        private val getSearchHistoryListUseCase: GetSearchHistoryListUseCase
) : ViewModel() {


    val filterList = getFilterListUseCase()

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    val searchHistory = getSearchHistoryListUseCase()
            .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    listOf()
            )

    val animeBySearch = getSearchAnimeResultsUseCase()
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
            .onStart { SearchAnimeScreenState.Loading }
            .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = SearchAnimeScreenState.Loading
            )

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {
            searchAnimeByNameUseCase(name)
            saveNameInAnimeSearchHistoryUseCase(name)
        }
    }

    fun clearAllFilters() {
        viewModelScope.launch {
            clearAllFiltersInFilterRepository()
            clearAllFiltersInSearchRepository()
        }
    }


}