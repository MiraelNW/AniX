package com.miraeldev.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Response(
    @SerialName("isLast") val isLast: Boolean,
    @SerialName("animeResponseDtoList") val results: List<AnimeInfoDto>,
)
