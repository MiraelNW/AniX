package com.miraeldev.local.dao.newCategory.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingNewCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_new_category_anime Order By page, releasedOn desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From paging_new_category_anime")
    suspend fun clearAllAnime()

}