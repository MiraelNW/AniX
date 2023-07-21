package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.CategoryModel
import kotlinx.coroutines.flow.StateFlow

interface FilterAnimeRepository {

    fun getGenreList(): StateFlow<List<CategoryModel>>

    fun getYearCategory(): StateFlow<String>

    fun getSortByCategory(): StateFlow<String>

    fun selectCategory(categoryId: Int, category: String)

    suspend fun clearAllFilters()
}