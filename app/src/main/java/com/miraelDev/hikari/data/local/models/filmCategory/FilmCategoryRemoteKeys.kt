package com.miraelDev.hikari.data.local.models.newCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_category_remote_key")
data class FilmCategoryRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val animeId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
)