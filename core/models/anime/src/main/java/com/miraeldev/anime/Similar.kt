package com.miraeldev.anime

data class Similar(
    val id: Int,
    val kind: String,
    val nameEn: String,
    val image: ImageModel,
    val score: Float,
    val nameRu: String,
)
