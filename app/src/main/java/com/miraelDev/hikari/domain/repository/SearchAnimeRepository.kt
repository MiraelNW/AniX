package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SearchAnimeRepository {

    fun getFilterList(): StateFlow<List<String>>

    suspend fun addToFilterList(categoryId: Int, category: String)

    suspend fun removeFromFilterList(categoryId: Int, category: String)

    suspend fun clearAllFilters()

    suspend fun searchAnimeByName(name: String)

    suspend fun saveNameInAnimeSearchHistory(name: String)

    fun saveSearchText(searchText:String)

    fun getSearchHistoryList():Flow<List<String>>

    fun getAnimeBySearch(): Flow<Result>

}