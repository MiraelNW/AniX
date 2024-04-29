package com.miraeldev.search.data.repository

import com.miraeldev.models.paging.PagingState
import kotlinx.coroutines.flow.Flow

interface SearchAnimeRepository {

    fun getFilterList(): Flow<List<String>>

    suspend fun addToFilterList(categoryId: Int, category: String)

    suspend fun removeFromFilterList(categoryId: Int, category: String)

    suspend fun clearAllFilters()

    suspend fun searchAnimeByName(name: String): Flow<PagingState>
    suspend fun loadSearchResultsNextPage()

    fun saveSearchText(searchText:String)

    suspend fun loadNextPage()
    fun getSearchInitialList(): Flow<PagingState>

    fun getSearchName():Flow<String>

    suspend fun saveNameInAnimeSearchHistory(name: String)

    fun getSearchHistoryListFlow():Flow<List<String>>

}