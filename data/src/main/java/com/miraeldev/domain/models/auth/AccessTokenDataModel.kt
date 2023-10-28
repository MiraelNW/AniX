package com.miraeldev.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class AccessTokenDataModel(
    @SerialName("access") val bearerToken: String,
)