package com.miraeldev.local.dao.filmCategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.local.models.filmCategory.FilmCategoryAnimeInfoDbModel

@Dao
interface FilmCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<FilmCategoryAnimeInfoDbModel>)

    @Query("Select * From film_category_anime limit 15")
    suspend fun getAnimeList(): List<AnimeInfo>

    @Query("SELECT (SELECT COUNT(*) FROM film_category_anime) == 0")
    suspend fun isEmpty(): Boolean

}