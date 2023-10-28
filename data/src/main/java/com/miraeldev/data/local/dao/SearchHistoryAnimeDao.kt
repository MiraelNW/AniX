package com.miraeldev.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.data.local.models.SearchHistoryDbModel
import kotlinx.coroutines.flow.Flow


@Dao
internal interface SearchHistoryAnimeDao {

    @Query("SELECT * FROM search_history")
    fun getSearchHistoryListFlow(): Flow<List<SearchHistoryDbModel>>

    @Query("SELECT * FROM search_history")
    suspend fun getSearchHistoryList(): List<SearchHistoryDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(searchItem: SearchHistoryDbModel)

}