package com.miraelDev.hikari.data.local.Dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.hikari.data.local.models.SearchHistoryDbModel
import kotlinx.coroutines.flow.Flow


@Dao
interface SearchHistoryAnimeDao {

    @Query("SELECT * FROM searchHistory")
    fun getSearchHistoryListFlow(): Flow<List<SearchHistoryDbModel>>

    @Query("SELECT * FROM searchHistory")
    suspend fun getSearchHistoryList(): List<SearchHistoryDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchItem(searchItem: SearchHistoryDbModel)

}