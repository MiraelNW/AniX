package com.miraeldev.local.dao.popularCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.local.models.popularCategory.PagingPopularCategoryAnimeInfoDbModel

@Dao
interface PopularCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingPopularCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_popular_category_anime order By page, score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From paging_popular_category_anime")
    suspend fun clearAllAnime()

}