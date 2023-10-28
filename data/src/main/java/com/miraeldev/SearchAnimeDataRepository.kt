package com.miraeldev

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import kotlinx.coroutines.flow.Flow

interface SearchAnimeDataRepository {

    fun getFilterList(): Flow<List<String>>

    suspend fun addToFilterList(categoryId: Int, category: String)

    suspend fun removeFromFilterList(categoryId: Int, category: String)

    suspend fun clearAllFilters()

    suspend fun searchAnimeByName(name: String): Flow<PagingData<AnimeInfo>>

    fun saveSearchText(searchText:String)

    fun getSearchResults(): Flow<Flow<PagingData<AnimeInfo>>>

    fun getSearchName():Flow<String>

    suspend fun saveNameInAnimeSearchHistory(name: String)


    fun getSearchHistoryListFlow():Flow<List<String>>

    suspend fun loadInitialList()

}