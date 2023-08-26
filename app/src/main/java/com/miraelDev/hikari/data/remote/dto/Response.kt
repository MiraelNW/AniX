package com.miraelDev.hikari.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
        @SerialName("count") val count: Int?,
        @SerialName("next") val next: String?,
        @SerialName("previous") val previous: String?,
        @SerialName("results") val results: List<AnimeInfoDto>,
)
