package com.miraeldev.data.local.dao.newCategory

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.newCategory.NewCategoryAnimeInfoDbModel
import com.miraeldev.data.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NewCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<NewCategoryAnimeInfoDbModel>)

    @Query("Select * From new_category_anime limit 15")
    fun getAnimeList(): Flow<List<AnimeInfo>>

    @Query("SELECT (SELECT COUNT(*) FROM new_category_anime) == 0")
    suspend fun isEmpty(): Boolean

    @Query("SELECT * FROM new_category_anime LIMIT 1")
    suspend fun getCreateTime(): NewCategoryAnimeInfoDbModel

}