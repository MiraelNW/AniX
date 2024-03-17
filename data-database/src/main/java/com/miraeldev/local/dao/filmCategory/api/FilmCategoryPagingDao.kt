package com.miraeldev.local.dao.filmCategory.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmCategoryPagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<PagingFilmCategoryAnimeInfoDbModel>)

    @Query("Select * From paging_film_category_anime Order By page,score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From paging_film_category_anime")
    suspend fun clearAllAnime()

}