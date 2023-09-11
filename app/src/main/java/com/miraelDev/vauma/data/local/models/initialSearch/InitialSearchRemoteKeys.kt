package com.miraelDev.vauma.data.local.models.initialSearch

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "initial_search_remote_key")
data class InitialSearchRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val animeId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)