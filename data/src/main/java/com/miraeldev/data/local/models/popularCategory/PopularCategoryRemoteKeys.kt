package com.miraeldev.data.local.models.popularCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_category_remote_key")
internal data class PopularCategoryRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val animeId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)