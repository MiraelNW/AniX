package com.miraeldev.api

import com.miraeldev.anime.CategoryModel
import kotlinx.coroutines.flow.StateFlow

interface FilterAnimeDataRepository {

    fun getGenreList(): StateFlow<List<CategoryModel>>

    fun getYearCategory(): StateFlow<String>

    fun getSortByCategory(): StateFlow<String>

    fun selectCategory(categoryId: Int, category: String)

    suspend fun clearAllFilters()
}