package com.miraeldev.data.local.dao.filmCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.filmCategory.FilmCategoryAnimeInfoDbModel

@Dao
internal interface FilmCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<FilmCategoryAnimeInfoDbModel>)

    @Query("Select * From film_category_anime Order By page,score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From film_category_anime")
    suspend fun clearAllAnime()

}