package com.miraeldev.api

import com.miraeldev.anime.CategoryModel
import kotlinx.coroutines.flow.Flow

interface FilterAnimeDataRepository {

    fun getGenreList(): Flow<List<CategoryModel>>

    fun getYearCategory(): Flow<String>

    fun getSortByCategory(): Flow<String>

    fun selectCategory(categoryId: Int, category: String)

    suspend fun clearAllFilters()
}