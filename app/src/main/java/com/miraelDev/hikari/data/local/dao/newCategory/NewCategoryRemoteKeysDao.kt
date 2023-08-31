package com.miraelDev.hikari.data.local.dao.newCategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.hikari.data.local.models.newCategory.NewCategoryRemoteKeys

@Dao
interface NewCategoryRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<NewCategoryRemoteKeys>)

    @Query("Select * From new_category_remote_key Where animeId = :id")
    suspend fun getRemoteKeyByAnimeId(id: Int): NewCategoryRemoteKeys?

    @Query("Delete From new_category_remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select createdAt From new_category_remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}