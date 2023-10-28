package com.miraeldev.data.local.dao.popularCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.popularCategory.PopularCategoryAnimeInfoDbModel

@Dao
internal interface PopularCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PopularCategoryAnimeInfoDbModel>)

    @Query("Select * From popular_category_anime order By page, score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From popular_category_anime")
    suspend fun clearAllAnime()

}