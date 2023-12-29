package com.miraeldev.anime

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class LastWatchedAnime(
    val id: Int,
    val imageUrl: String = "",
    val nameEn: String = "",
    val nameRu: String = "",
    val genres: ImmutableList<Genre> = persistentListOf(Genre("", "")),
    val isFavourite: Boolean = false,
    val episodeNumber :Int = -1,
    val videoUrl : String = ""
)

fun LastWatchedAnime.toAnimeInfo():AnimeInfo{
    return AnimeInfo(
        id= this.id,
        image = ImageModel(imageUrl,imageUrl),
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        genres = this.genres,
        isFavourite = this.isFavourite
    )
}
