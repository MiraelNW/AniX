package com.miraeldev.data.local.models.popularCategory

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraeldev.data.local.models.ImageDbModel
import com.miraeldev.domain.models.animeDataModels.GenreDataModel
import com.miraeldev.domain.models.animeDataModels.VideoInfoDataModel
import kotlinx.serialization.Serializable


@Entity(tableName = "popular_category_anime")
@Serializable
internal data class PopularCategoryAnimeInfoDbModel(

    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameRu: String,
    val image: ImageDbModel,
    val kind: String,
    val score: Float,
    val status: String,
    val rating: String,
    val releasedOn: String,
    val episodes: Int,
    val duration: Int,
    val description: String,
    val videoUrls: VideoInfoDataModel = VideoInfoDataModel(),
    val genres: List<GenreDataModel>,
    var page: Int,
)
