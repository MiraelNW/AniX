package com.miraeldev.anime

import kotlinx.collections.immutable.ImmutableList


data class AnimeDetailInfo(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
    val image: ImageModel,
    val kind: String,
    val score: Float,
    val status: String,
    val rating: String,
    val releasedOn: String,
    val episodes: Int,
    val duration: Int,
    val description: String,
    val videoUrls: VideoInfo = VideoInfo(),
    val isFavourite: Boolean,
    val genres: ImmutableList<Genre>,
    val similar: ImmutableList<Similar>
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

