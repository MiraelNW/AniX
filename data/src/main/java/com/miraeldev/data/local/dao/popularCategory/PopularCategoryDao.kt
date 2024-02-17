package com.miraeldev.data.local.dao.popularCategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.popularCategory.PopularCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PopularCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PopularCategoryAnimeInfoDbModel>)

    @Query("Select * From popular_category_anime limit 15")
    suspend fun getAnimeList(): List<AnimeInfo>

    @Query("SELECT (SELECT COUNT(*) FROM popular_category_anime) == 0")
    suspend fun isEmpty(): Boolean

}