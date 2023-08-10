package com.miraelDev.hikari.presentation.SearchAimeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.result.Result
import com.miraelDev.hikari.domain.usecases.SearchUsecase.GetSearchAnimeResultsUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.GetSearchHistoryListUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SaveNameInAnimeSearchHistoryUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.filterUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.hikari.domain.usecases.SearchUsecase.filterUsecase.GetFilterListUseCase
import com.miraelDev.hikari.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepository
import com.miraelDev.hikari.exntensions.mergeWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
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

    private val startLoad = MutableSharedFlow<SearchAnimeScreenState>()

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
            .onStart { SearchAnimeScreenState.Loading }
            .shareIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    replay = 1
            )


    val animeBySearch = searchResultsFlow
            .mergeWith(startLoad)


    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {
            startLoad.emit(SearchAnimeScreenState.Loading)
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