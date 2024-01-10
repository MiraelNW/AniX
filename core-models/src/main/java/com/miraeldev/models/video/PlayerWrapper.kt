package com.miraeldev.video

import androidx.media3.exoplayer.ExoPlayer

data class PlayerWrapper(
    val exoPlayer: ExoPlayer,
    val title: String ="",
    val isFirstEpisode: Boolean = false,
    val isLastEpisode: Boolean = false
)
