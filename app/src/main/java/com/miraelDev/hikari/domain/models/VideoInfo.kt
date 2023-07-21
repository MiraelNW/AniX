package com.miraelDev.hikari.domain.models


import androidx.media3.common.MediaItem


data class VideoInfo(
    val id: Int = 0,
    val imageUrl: String = "",
    val playerUrl: String = "",
    val mediaItem: MediaItem? = null,
    val videoName: String = "",
)
