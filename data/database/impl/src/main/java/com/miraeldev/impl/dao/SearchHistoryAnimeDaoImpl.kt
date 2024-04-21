package com.miraeldev.impl.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.miraeldev.Database
import com.miraeldev.api.SearchHistoryAnimeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class SearchHistoryAnimeDaoImpl(private val database: Database) : SearchHistoryAnimeDao {

    private val query = database.search_history_tableQueries
    private val ioDispatcher = Dispatchers.IO

    override fun getSearchHistoryListFlow(): Flow<List<String>> {
        return query.getSearchHistoryListFlow().asFlow().mapToList(ioDispatcher)
    }

    override suspend fun getSearchHistoryList(): List<String> {
        return query.getSearchHistoryList().executeAsList()
    }

    override suspend fun insertSearchItem(searchItem: String) {
        query.insertSearchItem(searchItem)
    }
}