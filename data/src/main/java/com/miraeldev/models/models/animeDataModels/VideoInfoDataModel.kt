package com.miraeldev.models.models.animeDataModels

import kotlinx.serialization.Serializable

@Serializable
data class VideoInfoDataModel(
    val id: Int = 0,
    val videoName: String = "",
    val videoImage: String = "",
    val videoUrl480: String = "",
    val videoUrl720: String = "",
    val videoUrl1080: String = "",
)