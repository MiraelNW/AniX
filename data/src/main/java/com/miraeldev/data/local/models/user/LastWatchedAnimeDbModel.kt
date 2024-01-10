package com.miraeldev.data.local.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.anime.VideoInfo
import com.miraeldev.data.local.models.VideoDbModel
import com.miraeldev.domain.models.animeDataModels.GenreDataModel
import com.miraeldev.domain.models.animeDataModels.toGenre
import com.miraeldev.models.anime.Genre
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
    val video: VideoDbModel
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
        video = this.video.toVideoInfo()
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
        video = this.video.toVideoDbModel()
    )
}

fun Genre.toDbModel(): GenreDataModel {
    return GenreDataModel(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}

fun VideoDbModel.toVideoInfo():VideoInfo{
    return VideoInfo(
        id = this.id,
        videoName = this.videoName,
        videoImage = this.videoImage,
        videoUrl480 = this.videoUrl480,
        videoUrl720 = this.videoUrl720,
        videoUrl1080 = this.videoUrl1080
    )
}
fun VideoInfo.toVideoDbModel():VideoDbModel{
    return VideoDbModel(
        id = this.id,
        videoName = this.videoName,
        videoImage = this.videoImage,
        videoUrl480 = this.videoUrl480,
        videoUrl720 = this.videoUrl720,
        videoUrl1080 = this.videoUrl1080
    )
}
