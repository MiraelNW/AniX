package com.miraeldev.models.dto

import com.miraeldev.models.anime.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("name") val nameEn: String,
    @SerialName("russian") val nameRu: String,
)

fun GenreDto.toModel(): Genre {
    return Genre(
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}
