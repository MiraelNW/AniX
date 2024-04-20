package com.miraeldev.impl.remote.dto

import com.miraeldev.data.remote.dto.AnimeInfoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Response(
    @SerialName("isLast") val isLast: Boolean,
    @SerialName("animeResponseDtoList") val results: List<AnimeInfoDto>,
)
