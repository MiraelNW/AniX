package com.miraeldev.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.local.models.SearchHistoryDbModel
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchHistoryAnimeDao {

    @Query("SELECT * FROM search_history")
    fun getSearchHistoryListFlow(): Flow<List<SearchHistoryDbModel>>

    @Query("SELECT * FROM search_history")
    suspend fun getSearchHistoryList(): List<SearchHistoryDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(searchItem: SearchHistoryDbModel)

}