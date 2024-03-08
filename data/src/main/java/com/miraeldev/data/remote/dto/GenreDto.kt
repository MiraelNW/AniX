package com.miraeldev.data.remote.dto

import com.miraeldev.local.animeDataModels.GenreDataModel
import com.miraeldev.models.anime.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
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
