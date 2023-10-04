package com.miraelDev.vauma.data.remote.dto

import com.miraelDev.vauma.domain.models.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val nameEn: String,
    @SerialName("russian") val nameRu: String,
)

fun GenreDto.toGenre():Genre{
    return Genre(
        id = this.id,
        nameRu = this.nameRu,
        nameEn = this.nameEn
    )
}
