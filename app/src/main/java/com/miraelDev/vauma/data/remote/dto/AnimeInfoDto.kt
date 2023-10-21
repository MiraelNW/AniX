package com.miraelDev.vauma.data.remote.dto

import com.google.common.collect.ImmutableList
import com.miraelDev.vauma.data.local.models.newCategory.FilmCategoryAnimeInfoDbModel
import com.miraelDev.vauma.data.local.models.newCategory.NameCategoryAnimeInfoDbModel
import com.miraelDev.vauma.data.local.models.newCategory.NewCategoryAnimeInfoDbModel
import com.miraelDev.vauma.data.local.models.initialSearch.InitialSearchAnimeInfoDbModel
import com.miraelDev.vauma.data.local.models.newCategory.PopularCategoryAnimeInfoDbModel
import com.miraelDev.vauma.domain.models.anime.AnimeDetailInfo
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeInfoDto(

        @SerialName("id") val id: Int,

        @SerialName("name") val name: String,

        @SerialName("russian") val russianName: String,

        @SerialName("image") val image: String,

        @SerialName("kind") val kind: String,

        @SerialName("score") val score: Float,

        @SerialName("aired_on") val airedOn: String,

        @SerialName("status") val status: String,

        @SerialName("episodes") val episodes: Int,

        @SerialName("rating") val rating: String,

        @SerialName("description") val description: String?,

        @SerialName("duration") val duration: Int,

        @SerialName("genres") val genres: List<GenreDto>,

        @SerialName("similar_animes") val similar: List<SimilarDto>,
)

internal fun AnimeInfoDto.toAnimeDetailInfo(): AnimeDetailInfo {
    return AnimeDetailInfo(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = ImmutableList.copyOf(this.genres.map { it.toGenre() }),
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
            similar = ImmutableList.copyOf(this.similar.map { it.toSimilar() })
    )
}

internal fun AnimeInfoDto.toAnimeInfo(): AnimeInfo {
    return AnimeInfo(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = ImmutableList.copyOf(this.genres.map { it.toGenre() }),
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
    )
}

internal fun AnimeInfoDto.mapToNewCategoryModel(): NewCategoryAnimeInfoDbModel {
    return NewCategoryAnimeInfoDbModel(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = this.genres.map { it.toGenre() },
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
            page = 0
    )
}

internal fun AnimeInfoDto.mapToPopularCategoryModel(): PopularCategoryAnimeInfoDbModel {
    return PopularCategoryAnimeInfoDbModel(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = this.genres.map { it.toGenre() },
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
            page = 0
    )
}
internal fun AnimeInfoDto.mapToFilmCategoryModel(): FilmCategoryAnimeInfoDbModel {
    return FilmCategoryAnimeInfoDbModel(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = this.genres.map { it.toGenre() },
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
            page = 0
    )
}

internal fun AnimeInfoDto.mapToNameCategoryModel(): NameCategoryAnimeInfoDbModel {
    return NameCategoryAnimeInfoDbModel(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = this.genres.map { it.toGenre() },
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
            page = 0
    )
}

internal fun AnimeInfoDto.mapToInitialSearchModel(): InitialSearchAnimeInfoDbModel {
    return InitialSearchAnimeInfoDbModel(
            id = this.id,
            nameRu = this.russianName,
            nameEn = this.name,
            description = this.description ?: "",
            rating = this.rating,
            score = this.score,
            airedOn = this.airedOn,
            status = this.status,
            kind = this.kind,
            genres = this.genres.map { it.toGenre() },
            episodes = this.episodes,
            image = this.image,
            duration = this.duration,
            page = 0
    )
}
