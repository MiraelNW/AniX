package com.miraelDev.anix.presentation.SearchAimeScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.anix.domain.usecases.SearchUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.anix.domain.usecases.SearchUsecase.GetFilterListUseCase
import com.miraelDev.anix.domain.usecases.filterUsecase.ClearAllFiltersInFilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val getFilterListUseCase: GetFilterListUseCase,

    private val clearAllFiltersInSearchRepository: ClearAllFiltersInSearchRepository,
    private val clearAllFiltersInFilterRepository: ClearAllFiltersInFilterRepository
) : ViewModel() {

    val filterList = getFilterListUseCase()

    private val _searchTextState = mutableStateOf("")
    val searchTextState = _searchTextState

    fun updateSearchTextState(value: String) {
        _searchTextState.value = value
    }

    fun clearAllFilters() {
        viewModelScope.launch {
            clearAllFiltersInFilterRepository()
            clearAllFiltersInSearchRepository()
        }
    }


}