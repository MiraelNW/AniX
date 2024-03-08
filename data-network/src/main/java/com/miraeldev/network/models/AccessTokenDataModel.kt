package com.miraeldev.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class AccessTokenDataModel(
    @SerialName("accessToken") val bearerToken: String,
    @SerialName("refreshToken") val refreshToken: String,
)