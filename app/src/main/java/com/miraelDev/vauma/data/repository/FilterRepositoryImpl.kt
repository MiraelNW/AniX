package com.miraelDev.vauma.data.repository

import android.content.Context
import com.miraelDev.vauma.R
import com.miraelDev.vauma.domain.models.CategoryModel
import com.miraelDev.vauma.domain.repository.FilterAnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    context : Context
) : FilterAnimeRepository {

    private val _filterCategoriesGenreList = mutableListOf(
        CategoryModel(context.getString(R.string.shounen), false),
        CategoryModel(context.getString(R.string.shojo), false),
        CategoryModel(context.getString(R.string.comedy), false),
        CategoryModel(context.getString(R.string.romance), false),
        CategoryModel(context.getString(R.string.school), false),
        CategoryModel(context.getString(R.string.martial_arts), false),
        CategoryModel(context.getString(R.string.harem), false),
        CategoryModel(context.getString(R.string.detective), false),
        CategoryModel(context.getString(R.string.drama), false),
        CategoryModel(context.getString(R.string.everyday_life), false),
        CategoryModel(context.getString(R.string.adventure), false),
        CategoryModel(context.getString(R.string.psychological), false),
        CategoryModel(context.getString(R.string.supernatural), false),
        CategoryModel(context.getString(R.string.sport), false),
        CategoryModel(context.getString(R.string.horror), false),
        CategoryModel(context.getString(R.string.fantastic), false),
        CategoryModel(context.getString(R.string.fantasy), false),
        CategoryModel(context.getString(R.string.action), false),
        CategoryModel(context.getString(R.string.thriller), false),
        CategoryModel(context.getString(R.string.superpower), false),
        CategoryModel(context.getString(R.string.gourmet), false),
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