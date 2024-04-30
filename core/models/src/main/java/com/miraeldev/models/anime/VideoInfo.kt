package com.miraeldev.anime

import kotlinx.serialization.Serializable

@Serializable
data class VideoInfo(
    val id: Int = 0,
    val videoName: String = "",
    val videoImage: String = "",
    val videoUrl480: String = "",
    val videoUrl720: String = "",
    val videoUrl1080: String = "",
)
