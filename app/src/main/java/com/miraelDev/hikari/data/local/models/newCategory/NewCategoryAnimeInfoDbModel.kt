package com.miraelDev.hikari.data.local.models.newCategory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.common.collect.ImmutableList
import com.miraelDev.hikari.domain.models.VideoInfo
import kotlinx.serialization.Serializable


@Entity(tableName = "new_category_anime")
@Serializable
data class NewCategoryAnimeInfoDbModel(

    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameRu: String,
    val image: String,
    val kind: String,
    val score: Float,
    val status: String,
    val rating: String,
    val airedOn: String,
    val episodes: Int,
    val duration: Int,
    val description: String,
    val videoUrls: VideoInfo = VideoInfo(),
    val genres: List<String>,
    var page: Int
)
