package com.miraeldev.video

data class PlayerWrapper(
    val link: String,
    val title: String = "",
    val isFirstEpisode: Boolean = false,
    val isLastEpisode: Boolean = false
)
