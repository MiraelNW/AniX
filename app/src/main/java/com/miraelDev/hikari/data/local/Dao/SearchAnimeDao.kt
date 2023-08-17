package com.miraelDev.hikari.data.local.Dao


import kotlinx.coroutines.flow.Flow


interface SearchAnimeDao {
    fun getSearchHistoryList(): Flow<List<String>>

    suspend fun insertSearchItem(searchItem: String)

}