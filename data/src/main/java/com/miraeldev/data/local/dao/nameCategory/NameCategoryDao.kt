package com.miraeldev.data.local.dao.nameCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.nameCategory.NameCategoryAnimeInfoDbModel

@Dao
internal interface NameCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<NameCategoryAnimeInfoDbModel>)

    @Query("Select * From name_category_anime Order By page, nameRu")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From name_category_anime")
    suspend fun clearAllAnime()

}