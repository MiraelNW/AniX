package com.miraeldev.data.local.dao.newCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.newCategory.NewCategoryAnimeInfoDbModel

@Dao
internal interface NewCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<NewCategoryAnimeInfoDbModel>)

    @Query("Select * From new_category_anime Order By page, releasedOn desc")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From new_category_anime")
    suspend fun clearAllAnime()

}