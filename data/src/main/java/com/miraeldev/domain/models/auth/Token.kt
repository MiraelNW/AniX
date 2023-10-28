package com.miraeldev.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Token(
    @SerialName("access") val bearerToken: String,
    @SerialName("refresh") val refreshToken: String,
)