package com.miraeldev.impl.models

import kotlinx.serialization.Serializable
@Serializable
data class VideoDbModel(
    val id: Int = 0,
    val videoName: String = "",
    val videoImage: String = "",
    val videoUrl480: String = "",
    val videoUrl720: String = "",
    val videoUrl1080: String = "",
)