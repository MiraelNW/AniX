package com.miraeldev.local.dao.nameCategory.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NameCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingNameCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_name_category_anime Order By page, nameRu")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From paging_name_category_anime")
    suspend fun clearAllAnime()

}