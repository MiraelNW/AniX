package com.miraelDev.vauma.domain.models.anime

import androidx.media3.exoplayer.ExoPlayer


data class PlayerWrapper(
        val exoPlayer: ExoPlayer,
        val title: String,
        val isFirstEpisode: Boolean,
        val isLastEpisode: Boolean
)