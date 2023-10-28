package com.miraeldev.data.local.dao.nameCategory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.data.local.models.nameCategory.NameCategoryRemoteKeys

@Dao
internal interface NameCategoryRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<NameCategoryRemoteKeys>)

    @Query("Select * From name_category_remote_key Where animeId = :id")
    suspend fun getRemoteKeyByAnimeId(id: Int): NameCategoryRemoteKeys?

    @Query("Delete From name_category_remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select createdAt From name_category_remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}