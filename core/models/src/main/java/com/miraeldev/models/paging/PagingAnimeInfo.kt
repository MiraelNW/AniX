package com.miraeldev.models.paging

import com.miraeldev.anime.ImageModel
import com.miraeldev.anime.VideoInfo
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.anime.Genre
import kotlinx.collections.immutable.toPersistentList

data class PagingAnimeInfo(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
    val image: ImageModel,
    val kind: String,
    val score: Float,
    val releasedOn: String,
    val status: String,
    val episodes: Int,
    val rating: String,
    val description: String?,
    val duration: Int,
    val genres: List<Genre>,
    val videos: VideoInfo,
    val isFavourite: Boolean,
    val page: Long,
    val isLast: Boolean,
    val createAt: Long
)

fun PagingAnimeInfo.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this.id,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.toPersistentList(),
        episodes = this.episodes,
        image = this.image,
        duration = this.duration,
        isFavourite = this.isFavourite
    )
}
