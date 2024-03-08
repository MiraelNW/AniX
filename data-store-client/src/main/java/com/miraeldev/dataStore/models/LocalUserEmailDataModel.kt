package com.miraeldev.dataStore.models

import kotlinx.serialization.Serializable

@Serializable
data class LocalUserEmailDataModel(
    val email: String,
)