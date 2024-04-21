package com.miraeldev.api


import kotlinx.coroutines.flow.Flow


interface SearchHistoryAnimeDao {

    fun getSearchHistoryListFlow(): Flow<List<String>>

    suspend fun getSearchHistoryList(): List<String>

    suspend fun insertSearchItem(searchItem: String)

}