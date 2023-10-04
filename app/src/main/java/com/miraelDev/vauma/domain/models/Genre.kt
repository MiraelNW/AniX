package com.miraelDev.vauma.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int,
    val nameEn: String,
    val nameRu: String,
)
