package com.miraeldev.data.remote.dto

import com.miraeldev.anime.Genre
import com.miraeldev.domain.models.animeDataModels.GenreDataModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GenreDto(
    @SerialName("name") val nameEn: String,
    @SerialName("russian") val nameRu: String,
)

internal fun GenreDto.toGenre(): Genre {
    return Genre(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}

internal fun GenreDto.toGenreDataModel(): GenreDataModel {
    return GenreDataModel(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}
