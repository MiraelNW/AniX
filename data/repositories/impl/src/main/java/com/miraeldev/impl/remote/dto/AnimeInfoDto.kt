package com.miraeldev.data.remote.dto

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.local.models.filmCategory.PagingFilmCategoryAnimeInfoDbModel
import com.miraeldev.local.models.nameCategory.PagingNameCategoryAnimeInfoDbModel
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import com.miraeldev.local.models.popularCategory.PagingPopularCategoryAnimeInfoDbModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeInfoDto(

    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("russian") val russianName: String,
    @SerialName("image") val image: ImageModelDto,
    @SerialName("kind") val kind: String,
    @SerialName("score") val score: Float,
    @SerialName("released_on") val releasedOn: String,
    @SerialName("status") val status: String,
    @SerialName("episodes") val episodes: Int,
    @SerialName("rating") val rating: String,
    @SerialName("description") val description: String?,
    @SerialName("duration") val duration: Int,
    @SerialName("genres") val genres: List<GenreDto>,
    @SerialName("similar") val similar: List<SimilarDto>,
    @SerialName("videos") val videos: List<VideoDto>,
    @SerialName("favoured") val isFavourite: Boolean,
)

fun AnimeInfoDto.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenre() }.toPersistentList(),
        episodes = this.episodes,
        image = this.image.toModel(),
        duration = this.duration,
        isFavourite = this.isFavourite
    )
}

fun AnimeDetailInfo.toLastWatched(): LastWatchedAnime {
    return LastWatchedAnime(
        id= this.id,
        imageUrl = this.image.original,
        nameRu = this.nameRu,
        nameEn = this.nameEn,
        genres = this.genres,
        isFavourite = this.isFavourite,
        video = this.videos[0],
        episodeNumber = 0
    )
}

fun AnimeInfoDto.toAnimeDetailInfo(): AnimeDetailInfo {
    return AnimeDetailInfo(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenre() }.toPersistentList(),
        episodes = this.episodes,
        image = this.image.toModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        similar = this.similar.map { it.toVideoInfo() }.toPersistentList(),
        videos = this.videos.map { it.toVideoInfo() }.toPersistentList()
    )
}

fun AnimeInfoDto.mapToPagingNewCategoryModel(): PagingNewCategoryAnimeInfoDbModel {
    return PagingNewCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

fun AnimeInfoDto.mapToPagingPopularCategoryModel(): PagingPopularCategoryAnimeInfoDbModel {
    return PagingPopularCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

fun AnimeInfoDto.mapToPagingFilmCategoryModel(): PagingFilmCategoryAnimeInfoDbModel {
    return PagingFilmCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}

fun AnimeInfoDto.mapToPagingNameCategoryModel(): PagingNameCategoryAnimeInfoDbModel {
    return PagingNameCategoryAnimeInfoDbModel(
        id = this.id,
        nameRu = this.russianName,
        nameEn = this.name,
        description = this.description ?: "",
        rating = this.rating,
        score = this.score,
        releasedOn = this.releasedOn,
        status = this.status,
        kind = this.kind,
        genres = this.genres.map { it.toGenreDataModel() },
        episodes = this.episodes,
        image = this.image.toDbModel(),
        duration = this.duration,
        isFavourite = this.isFavourite,
        page = 0
    )
}
