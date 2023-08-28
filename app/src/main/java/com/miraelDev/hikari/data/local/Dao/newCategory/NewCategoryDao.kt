package com.miraelDev.hikari.data.local.Dao.newCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.hikari.data.local.models.newCategory.NewCategoryAnimeInfoDbModel
import com.miraelDev.hikari.data.local.models.newCategory.PopularCategoryAnimeInfoDbModel
import com.miraelDev.hikari.domain.models.AnimeInfo

@Dao
interface NewCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<NewCategoryAnimeInfoDbModel>)

    @Query("Select * From new_category_anime Order By page")
    fun getAnime(): PagingSource<Int, AnimeInfo>

    @Query("Delete From new_category_anime")
    suspend fun clearAllAnime()

}