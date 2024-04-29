package com.miraeldev.impl.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("token") val bearerToken: String,
    @SerialName("refreshToken") val refreshToken: String,
)