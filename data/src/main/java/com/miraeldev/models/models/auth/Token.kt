package com.miraeldev.models.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Token(
    @SerialName("token") val bearerToken: String,
    @SerialName("refreshToken") val refreshToken: String,
)