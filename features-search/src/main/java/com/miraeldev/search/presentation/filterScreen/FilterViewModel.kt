package com.miraeldev.search.presentation.filterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraeldev.search.domain.usecases.filterUseCase.AddToFilterListUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.ClearAllFiltersUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetGenreListUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetSortByCategoryUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.GetYearCategoryUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.RemoveFromFilterListUseCase
import com.miraeldev.search.domain.usecases.filterUseCase.SelectCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(

    private val getGenreListUseCase: GetGenreListUseCase,
    private val getSortByCategoryUseCase: GetSortByCategoryUseCase,
    private val getYearCategoryUseCase: GetYearCategoryUseCase,

    private val addToFilterListUseCase: AddToFilterListUseCase,
    private val removeFromFilterListUseCase: RemoveFromFilterListUseCase,

    private val selectCategoryUseCase: SelectCategoryUseCase,

    private val clearAllFiltersInFilterRepository: ClearAllFiltersUseCase

) : ViewModel() {

    val genreListFlow = getGenreListUseCase()

    val sortByCategoryFlow = getSortByCategoryUseCase()

    val yearCategoryFlow = getYearCategoryUseCase()

    fun selectCategory(categoryId: Int, category: String, isSelected: Boolean) {
        viewModelScope.launch {
            selectCategoryUseCase(categoryId, category)
            if (isSelected) {
                removeFromFilterListUseCase(categoryId, category)
            } else {
                addToFilterListUseCase(categoryId, category)
            }
        }
    }

    fun clearAllFilters() {
        viewModelScope.launch {
            clearAllFiltersInFilterRepository()
        }
    }
}