package com.miraeldev.data.local.dao.nameCategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.nameCategory.NameCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NameCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<NameCategoryAnimeInfoDbModel>)

    @Query("Select * From name_category_anime limit 15")
    suspend fun getAnimeList(): List<AnimeInfo>

    @Query("SELECT (SELECT COUNT(*) FROM name_category_anime) == 0")
    suspend fun isEmpty(): Boolean

}