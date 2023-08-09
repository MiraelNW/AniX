package com.miraelDev.hikari.data.local.Dao


import com.miraelDev.hikari.data.local.models.SearchAnimeDbModel
import hikari.searchDb.SearchHistory
import kotlinx.coroutines.flow.Flow


interface SearchAnimeDao {
    fun getSearchHistoryList(): Flow<List<String>>

    suspend fun insertSearchItem(searchItem: String)
}