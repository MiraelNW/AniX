package com.miraelDev.anix.presentation.SearchAimeScreen.FilterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.anix.domain.usecases.SearchUsecase.AddToFilterListUseCase
import com.miraelDev.anix.domain.usecases.SearchUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.anix.domain.usecases.SearchUsecase.RemoveFromFilterListUseCase
import com.miraelDev.anix.domain.usecases.filterUsecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(

    private val getGenreListUseCase: GetGenreListUseCase,
    private val getSortByCategoryUseCase: GetSortByCategoryUseCase,
    private val getYearCategoryUseCase: GetYearCategoryUseCase,

    private val addToFilterListUseCase: AddToFilterListUseCase,
    private val removeFromFilterListUseCase: RemoveFromFilterListUseCase,

    private val selectCategoryUseCase: SelectCategoryUseCase,

    private val clearAllFiltersInFilterRepository: ClearAllFiltersInFilterRepository,
    private val clearAllFiltersInSearchRepository: ClearAllFiltersInSearchRepository

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