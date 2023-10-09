package com.miraelDev.vauma.domain.models.anime

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
)
