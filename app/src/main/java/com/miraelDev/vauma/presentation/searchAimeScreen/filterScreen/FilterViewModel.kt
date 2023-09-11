package com.miraelDev.vauma.presentation.searchAimeScreen.filterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase.AddToFilterListUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase.ClearAllFiltersInSearchRepositoryUseCase
import com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase.RemoveFromFilterListUseCase
import com.miraelDev.vauma.domain.usecases.filterUsecase.*
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

    private val clearAllFiltersInFilterRepository: ClearAllFiltersInFilterRepositoryUseCase,
    private val clearAllFiltersInSearchRepository: ClearAllFiltersInSearchRepositoryUseCase

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
            clearAllFiltersInSearchRepository()
        }
    }
}