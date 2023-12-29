package com.miraeldev.data.local.models.initialSearch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "initial_search_remote_key")
internal data class InitialSearchRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val animeId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)