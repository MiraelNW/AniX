package com.miraelDev.anix.domain.models

import com.google.android.exoplayer2.MediaItem

data class VideoInfo(
    val id: Int = 0,
    val imageUrl: String = "",
    val playerUrl: String = "",
    val mediaItem: MediaItem? = null,
    val videoName: String = "",
)
