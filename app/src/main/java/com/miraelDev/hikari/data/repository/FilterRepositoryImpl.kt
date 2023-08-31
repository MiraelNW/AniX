package com.miraelDev.hikari.data.repository

import com.miraelDev.hikari.domain.models.CategoryModel
import com.miraelDev.hikari.domain.repository.FilterAnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(

) : FilterAnimeRepository {

    private val _filterCategoriesGenreList = mutableListOf(
        CategoryModel("Сенен", false),
        CategoryModel("Седзе", false),
        CategoryModel("Комедия", false),
        CategoryModel("Романтика", false),
        CategoryModel("Школа", false),
        CategoryModel("Боевые искусства", false),
        CategoryModel("Гарем", false),
        CategoryModel("Детектив", false),
        CategoryModel("Драма", false),
        CategoryModel("Повседневность", false),
        CategoryModel("Приключение", false),
        CategoryModel("Психологическое", false),
        CategoryModel("Сверхъестественное", false),
        CategoryModel("Спорт", false),
        CategoryModel("Ужасы", false),
        CategoryModel("Фантастика", false),
        CategoryModel("Фэнтези", false),
        CategoryModel("Экшен", false),
        CategoryModel("Триллер", false),
        CategoryModel("Супер сила", false),
        CategoryModel("Гурман", false),
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