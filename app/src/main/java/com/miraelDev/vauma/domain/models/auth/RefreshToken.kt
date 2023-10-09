package com.miraelDev.vauma.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class RefreshToken(
    @SerialName("refresh") val refresh: String,
)