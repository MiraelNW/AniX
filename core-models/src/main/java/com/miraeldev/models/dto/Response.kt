package com.miraeldev.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("isLast") val isLast: Boolean,
    @SerialName("animeResponseDtoList") val results: List<AnimeInfoDto>,
)
