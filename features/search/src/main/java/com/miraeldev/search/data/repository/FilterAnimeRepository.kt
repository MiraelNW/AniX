package com.miraeldev.search.data.repository

import com.miraeldev.anime.CategoryModel
import kotlinx.coroutines.flow.Flow

interface FilterAnimeRepository {

    fun getGenreList(): Flow<List<CategoryModel>>

    fun getYearCategory(): Flow<String>

    fun getSortByCategory(): Flow<String>

    fun selectCategory(categoryId: Int, category: String)

    suspend fun clearAllFilters()
}