package com.miraeldev.api.models

import kotlinx.serialization.Serializable

@Serializable
data class LocalUserEmailDataModel(
    val email: String,
)