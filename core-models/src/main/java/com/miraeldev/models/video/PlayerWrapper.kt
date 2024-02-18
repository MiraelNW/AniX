package com.miraeldev.video

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

data class PlayerWrapper(
    val link: String,
    val title: String ="",
    val isFirstEpisode: Boolean = false,
    val isLastEpisode: Boolean = false
)
