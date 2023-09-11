package com.miraelDev.vauma.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarListDto(
    @SerialName("results") val results: List<SimilarDto>,
)
