package com.miraelDev.hikari.presentation.SearchAimeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.usecases.SearchUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.hikari.domain.usecases.SearchUsecase.GetFilterListUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.GetSearchAnimeResultsUseCase
import com.miraelDev.hikari.domain.usecases.SearchUsecase.SearchAnimeByNameUseCase
import com.miraelDev.hikari.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val getFilterListUseCase: GetFilterListUseCase,

    private val clearAllFiltersInSearchRepository: ClearAllFiltersInSearchRepository,
    private val clearAllFiltersInFilterRepository: ClearAllFiltersInFilterRepository,

    private val searchAnimeByNameUseCase: SearchAnimeByNameUseCase,
    private val getSearchAnimeResultsUseCase: GetSearchAnimeResultsUseCase
) : ViewModel() {



    val filterList = getFilterListUseCase()

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    val animeBySearch = getSearchAnimeResultsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    fun searchAnimeByName(name: String) {
        viewModelScope.launch {
            searchAnimeByNameUseCase(name)
        }
    }

    fun clearAllFilters() {
        viewModelScope.launch {
            clearAllFiltersInFilterRepository()
            clearAllFiltersInSearchRepository()
        }
    }


}