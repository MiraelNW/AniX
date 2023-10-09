package com.miraelDev.vauma.data.local.models.newCategory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraelDev.vauma.domain.models.anime.Genre
import com.miraelDev.vauma.domain.models.anime.VideoInfo
import kotlinx.serialization.Serializable


@Entity(tableName = "name_category_anime")
@Serializable
data class NameCategoryAnimeInfoDbModel(

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
    val genres: List<Genre>,
    var page: Int
)
