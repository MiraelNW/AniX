package com.miraelDev.hikari.domain.repository

import androidx.paging.PagingData
import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow

interface SearchAnimeRepository {

    fun getFilterList(): Flow<List<String>>

    suspend fun addToFilterList(categoryId: Int, category: String)

    suspend fun removeFromFilterList(categoryId: Int, category: String)

    suspend fun clearAllFilters()

    suspend fun searchAnimeByName(name: String): Flow<PagingData<AnimeInfo>>

    fun getSearchResults(): Flow<Flow<PagingData<AnimeInfo>>>

    fun getSearchName():Flow<String>

    suspend fun saveNameInAnimeSearchHistory(name: String)

    fun saveSearchText(searchText:String)

    fun getSearchHistoryListFlow():Flow<List<String>>

    suspend fun loadInitialList()

}