package com.miraeldev.anime

import com.miraeldev.models.anime.Genre
import com.miraeldev.models.anime.Similar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class AnimeDetailInfo(
    val id: Int = 0,
    val nameEn: String = "",
    val nameRu: String = "",
    val image: ImageModel = ImageModel(),
    val kind: String = "",
    val score: Float = 0f,
    val status: String = "",
    val rating: String = "",
    val releasedOn: String = "",
    val episodes: Int = 0,
    val duration: Int = 0,
    val description: String = "",
    val isFavourite: Boolean = false,
    val genres: ImmutableList<Genre> = persistentListOf(),
    val similar: ImmutableList<Similar> = persistentListOf(),
    val videos: ImmutableList<VideoInfo> = persistentListOf()
)

fun AnimeDetailInfo.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this.id,
        nameEn = this.nameEn,
        nameRu = this.nameRu,
        image = this.image,
        kind = this.kind,
        score = this.score,
        status = this.status,
        rating = this.rating,
        releasedOn = this.releasedOn,
        episodes = this.episodes,
        duration = this.duration,
        description = this.description,
        videoUrls = VideoInfo(),
        genres = this.genres,
        isFavourite = this.isFavourite
    )
}

