package com.miraeldev.models.anime

import com.miraeldev.anime.ImageModel

data class Similar(
    val id: Int = 0,
    val kind: String = "",
    val nameEn: String = "",
    val image: ImageModel = ImageModel(),
    val score: Float = 0f,
    val nameRu: String = "",
)
