package com.miraeldev.data.remote.dto

import com.miraeldev.anime.VideoInfo
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val id: Int = 0,
    val videoName: String = "",
    val videoImage: String = "",
    val videoUrl480: String = "",
    val videoUrl720: String = "",
    val videoUrl1080: String = "",
)

internal fun VideoDto.toVideoInfo(): VideoInfo {
    return VideoInfo(
        id = this.id,
        videoName = this.videoName,
        videoImage = this.videoImage,
        videoUrl480 = this.videoUrl480,
        videoUrl720 = this.videoUrl720,
        videoUrl1080 = this.videoUrl1080
    )
}