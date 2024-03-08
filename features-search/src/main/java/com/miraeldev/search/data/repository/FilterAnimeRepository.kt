package com.miraeldev.search.data.repository

import com.miraeldev.anime.CategoryModel
import kotlinx.coroutines.flow.StateFlow

interface FilterAnimeRepository {

    fun getGenreList(): StateFlow<List<CategoryModel>>

    fun getYearCategory(): StateFlow<String>

    fun getSortByCategory(): StateFlow<String>

    fun selectCategory(categoryId: Int, category: String)

    suspend fun clearAllFilters()
}