package com.miraeldev.data.local.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraeldev.anime.Genre
import com.miraeldev.domain.models.animeDataModels.GenreDataModel
import com.miraeldev.domain.models.animeDataModels.toGenre
import com.miraeldev.anime.LastWatchedAnime
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class LastWatchedAnimeDbModel(

    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val nameEn: String,
    val nameRu: String,
    val genres: List<GenreDataModel>,
    val isFavourite: Boolean,
    val episodeNumber: Int,
    val videoUrl: String
)

fun LastWatchedAnimeDbModel.toModel(): LastWatchedAnime {
    return LastWatchedAnime(
        id = this.id,
        imageUrl = this.imageUrl,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        genres = this.genres.map { it.toGenre() }.toImmutableList(),
        isFavourite = this.isFavourite,
        episodeNumber = this.episodeNumber,
        videoUrl = this.videoUrl
    )
}

fun LastWatchedAnime.toDbModel(): LastWatchedAnimeDbModel {
    return LastWatchedAnimeDbModel(
        id = this.id,
        imageUrl = this.imageUrl,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        genres = this.genres.map { it.toDbModel() },
        isFavourite = this.isFavourite,
        episodeNumber = this.episodeNumber,
        videoUrl = this.videoUrl
    )
}

fun Genre.toDbModel(): GenreDataModel {
    return GenreDataModel(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}
