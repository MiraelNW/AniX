package com.miraeldev.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RefreshToken(
    @SerialName("refresh") val refresh: String,
)