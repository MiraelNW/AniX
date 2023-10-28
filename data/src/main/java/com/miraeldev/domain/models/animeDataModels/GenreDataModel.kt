package com.miraeldev.domain.models.animeDataModels

import com.miraeldev.anime.Genre
import kotlinx.serialization.Serializable


@Serializable
data class GenreDataModel(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
)

fun GenreDataModel.toGenre(): Genre {
    return Genre(
        this.id,
        this.nameEn,
        this.nameRu
    )
}