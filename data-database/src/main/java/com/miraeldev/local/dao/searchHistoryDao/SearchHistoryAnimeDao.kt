package com.miraeldev.local.dao.searchHistoryDao


import kotlinx.coroutines.flow.Flow
import tables.SearchHistoryDbModel


interface SearchHistoryAnimeDao {

    fun getSearchHistoryListFlow(): Flow<List<String>>

    suspend fun getSearchHistoryList(): List<String>

    suspend fun insertSearchItem(searchItem: String)

}