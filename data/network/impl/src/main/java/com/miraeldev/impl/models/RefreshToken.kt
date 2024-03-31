package com.miraeldev.impl.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshToken(
    @SerialName("refreshToken") val refresh: String,
)