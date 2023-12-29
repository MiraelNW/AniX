package com.miraeldev.data.local.models.newCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "new_category_remote_key")
internal data class NewCategoryRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val animeId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)