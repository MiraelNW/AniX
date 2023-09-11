package com.miraelDev.vauma.data.local.dao.newCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.vauma.data.local.models.newCategory.FilmCategoryAnimeInfoDbModel
import com.miraelDev.vauma.domain.models.AnimeInfo

@Dao
interface FilmCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<FilmCategoryAnimeInfoDbModel>)

    @Query("Select * From film_category_anime Order By page,score desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From film_category_anime")
    suspend fun clearAllAnime()

}