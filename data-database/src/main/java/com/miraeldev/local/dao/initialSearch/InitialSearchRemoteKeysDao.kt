package com.miraeldev.local.dao.initialSearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraeldev.local.models.initialSearch.InitialSearchRemoteKeys

@Dao
interface InitialSearchRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<InitialSearchRemoteKeys>)

    @Query("Select * From initial_search_remote_key Where animeId = :id")
    suspend fun getRemoteKeyByAnimeId(id: Int): InitialSearchRemoteKeys?

    @Query("Delete From initial_search_remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select createdAt From initial_search_remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}