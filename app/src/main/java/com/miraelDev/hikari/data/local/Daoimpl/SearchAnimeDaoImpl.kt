package com.miraelDev.hikari.data.local.Dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.miraelDev.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchAnimeDaoImpl @Inject constructor(
        private val db: AppDatabase
) : SearchAnimeDao {

    private val queries = db.searchQueries

    private val dispatcher = Dispatchers.IO
    override fun getSearchHistoryList(): Flow<List<String>> =
            queries.getSearchHistory().asFlow().mapToList(dispatcher)

    override suspend fun insertSearchItem(searchItem: String) {

        val searchList = queries.getSearchHistory().executeAsList()
        val mutableSearchList = ArrayDeque(searchList)
        mutableSearchList.remove(searchItem)

        if (searchList.size == 20) {
            mutableSearchList.addFirst(searchItem)
            mutableSearchList.removeLast()
        } else {
            mutableSearchList.addFirst(searchItem)
        }

        mutableSearchList.forEach {
            queries.insertSearchHistoryItem(it)
        }

    }
}