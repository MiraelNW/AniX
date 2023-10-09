package com.miraelDev.vauma.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Token(
    @SerialName("access") val bearerToken: String,
    @SerialName("refresh") val refreshToken: String,
)