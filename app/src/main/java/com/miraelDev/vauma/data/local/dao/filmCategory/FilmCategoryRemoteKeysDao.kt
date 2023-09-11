package com.miraelDev.vauma.data.local.dao.newCategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.vauma.data.local.models.newCategory.FilmCategoryRemoteKeys

@Dao
interface FilmCategoryRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<FilmCategoryRemoteKeys>)

    @Query("Select * From film_category_remote_key Where animeId = :id")
    suspend fun getRemoteKeyByAnimeId(id: Int): FilmCategoryRemoteKeys?

    @Query("Delete From film_category_remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select createdAt From film_category_remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}