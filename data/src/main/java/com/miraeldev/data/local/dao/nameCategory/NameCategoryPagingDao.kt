package com.miraeldev.data.local.dao.nameCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NameCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingNameCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_name_category_anime Order By page, nameRu")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Select * From paging_name_category_anime limit 15")
    fun getAnimeList(): Flow<List<AnimeInfo>>

    @Query("SELECT (SELECT COUNT(*) FROM paging_name_category_anime) == 0")
    suspend fun isEmpty(): Boolean

    @Query("Delete From paging_name_category_anime")
    suspend fun clearAllAnime()

}