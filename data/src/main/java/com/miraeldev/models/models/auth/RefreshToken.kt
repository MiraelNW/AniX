package com.miraeldev.models.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshToken(
    @SerialName("refreshToken") val refresh: String,
)