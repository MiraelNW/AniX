package com.miraeldev.data.local.dao.filmCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface FilmCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingFilmCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_film_category_anime Order By page,score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Select * From paging_film_category_anime limit 15")
    fun getAnimeList(): Flow<List<AnimeInfo>>

    @Query("SELECT (SELECT COUNT(*) FROM paging_film_category_anime) == 0")
    suspend fun isEmpty(): Boolean

    @Query("Delete From paging_film_category_anime")
    suspend fun clearAllAnime()

}