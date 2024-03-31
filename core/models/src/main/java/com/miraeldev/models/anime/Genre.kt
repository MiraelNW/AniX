package com.miraeldev.models.anime

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val nameEn: String,
    val nameRu: String,
)
