package com.miraeldev.data.local.models.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.data.local.models.user.ImageDbModel
import com.miraeldev.data.local.models.user.toModel
import com.miraeldev.domain.models.animeDataModels.GenreDataModel
import com.miraeldev.domain.models.animeDataModels.VideoInfoDataModel
import com.miraeldev.domain.models.animeDataModels.toGenre
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable


@Entity(tableName = "favList")
@Serializable
data class AnimeInfoDbModel(

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
    val isFavourite: Boolean,
    var page: Int
)

internal fun AnimeInfoDbModel.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this.id,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        description = this.description,
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenre() }.toImmutableList(),
        episodes = this.episodes,
        duration = this.duration,
        image = this.image.toModel(),
        isFavourite = this.isFavourite
    )
}

