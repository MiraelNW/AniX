package com.miraeldev.data.local.dao.newCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NewCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingNewCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_new_category_anime Order By page, releasedOn desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Select * From paging_new_category_anime limit 15")
    fun getAnimeList(): Flow<List<AnimeInfo>>

    @Query("SELECT (SELECT COUNT(*) FROM paging_new_category_anime) == 0")
    suspend fun isEmpty(): Boolean

    @Query("Delete From paging_new_category_anime")
    suspend fun clearAllAnime()

}