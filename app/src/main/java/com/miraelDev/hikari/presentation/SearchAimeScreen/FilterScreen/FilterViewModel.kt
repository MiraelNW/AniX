package com.miraelDev.hikari.presentation.SearchAimeScreen.FilterScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.AddToFilterListUseCase
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.ClearAllFiltersInSearchRepository
import com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase.RemoveFromFilterListUseCase
import com.miraelDev.hikari.domain.usecases.filterUsecase.*
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