package com.miraeldev.anime

import kotlinx.serialization.Serializable

@Serializable
data class ImageModel(
    val preview: String,
    val original: String,
    val token: String = ""
)
