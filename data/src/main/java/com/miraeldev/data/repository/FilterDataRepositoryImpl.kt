package com.miraeldev.data.repository

import com.miraeldev.FilterAnimeDataRepository
import com.miraeldev.anime.CategoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

internal class FilterDataRepositoryImpl @Inject constructor() : FilterAnimeDataRepository {


    private val _filterCategoriesGenreList = mutableListOf(
        CategoryModel("shounen", false),
        CategoryModel("shojo", false),
        CategoryModel("comedy", false),
        CategoryModel("romance", false),
        CategoryModel("school", false),
        CategoryModel("martial_arts", false),
        CategoryModel("harem", false),
        CategoryModel("detective", false),
        CategoryModel("drama", false),
        CategoryModel("everyday_life", false),
        CategoryModel("adventure", false),
        CategoryModel("psychological", false),
        CategoryModel("supernatural", false),
        CategoryModel("sport", false),
        CategoryModel("horror", false),
        CategoryModel("fantastic", false),
        CategoryModel("fantasy", false),
        CategoryModel("action", false),
        CategoryModel("thriller", false),
        CategoryModel("superpower", false),
        CategoryModel("gourmet", false),
    )

    private val filterCategoriesGenreList: List<CategoryModel>
        get() = _filterCategoriesGenreList.toList()

    private val _genreListFlow = MutableStateFlow(filterCategoriesGenreList)

    private val _yearFlow = MutableStateFlow("")

    private val _sortByFlow = MutableStateFlow("")

    override fun getGenreList(): StateFlow<List<CategoryModel>> = _genreListFlow.asStateFlow()

    override fun getYearCategory(): StateFlow<String> = _yearFlow.asStateFlow()

    override fun getSortByCategory(): StateFlow<String> = _sortByFlow.asStateFlow()

    override fun selectCategory(categoryId: Int, category: String) {
        when (categoryId) {
            1 -> {
                if (_yearFlow.value == category) {
                    _yearFlow.value = ""
                } else {
                    _yearFlow.value = category
                }
            }

            2 -> {
                if (_sortByFlow.value == category) {
                    _sortByFlow.value = ""
                } else {
                    _sortByFlow.value = category
                }
            }

            else -> {
                _filterCategoriesGenreList.forEach {
                    if (it.name == category) {
                        val newCategoryState = it.copy(isSelected = !it.isSelected)
                        val indexOfNewCategory = _filterCategoriesGenreList.indexOf(it)
                        _filterCategoriesGenreList[indexOfNewCategory] = newCategoryState
                    }
                }
                _genreListFlow.value = filterCategoriesGenreList
            }
        }

    }

    override suspend fun clearAllFilters() {
        _yearFlow.value = ""
        _sortByFlow.value = ""
        _filterCategoriesGenreList.forEach {
            val newCategoryState = it.copy(isSelected = false)
            val indexOfNewCategory = _filterCategoriesGenreList.indexOf(it)
            _filterCategoriesGenreList[indexOfNewCategory] = newCategoryState
        }
        _genreListFlow.value = filterCategoriesGenreList
    }

}