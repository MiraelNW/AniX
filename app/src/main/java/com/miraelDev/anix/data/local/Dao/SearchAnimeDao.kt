package com.miraelDev.anix.data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.anix.data.local.models.SearchAnimeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchAnimeDao {

    @Query("SELECT * FROM SearchAnimeTable")
    fun getAnimeList(): Flow<List<SearchAnimeDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeInfo(searchAnimeDbModel: SearchAnimeDbModel)
}