package com.miraeldev.local.models.initialSearch

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraeldev.local.animeDataModels.GenreDataModel
import com.miraeldev.local.models.user.ImageDbModel
import com.miraeldev.models.models.animeDataModels.VideoInfoDataModel
import kotlinx.serialization.Serializable


@Entity(tableName = "paging_initial_search_anime")
@Serializable
data class PagingInitialSearchAnimeInfoDbModel(

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
    val isFavourite:Boolean,
    var page: Int,
)
