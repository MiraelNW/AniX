package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SearchAnimeRepository {

    fun getFilterList(): StateFlow<List<String>>

    suspend fun addToFilterList(categoryId: Int, category: String)

    suspend fun removeFromFilterList(categoryId: Int, category: String)

    suspend fun clearAllFilters()

    suspend fun searchAnimeByName(name: String)

    fun getAnimeBySearch(): Flow<List<AnimeInfo>>

}