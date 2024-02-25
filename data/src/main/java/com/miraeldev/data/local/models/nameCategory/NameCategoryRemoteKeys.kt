package com.miraeldev.data.local.models.nameCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name_category_remote_key")
data class NameCategoryRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val animeId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)