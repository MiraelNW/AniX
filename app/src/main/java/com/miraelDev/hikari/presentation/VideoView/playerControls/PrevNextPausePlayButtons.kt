package com.miraelDev.hikari.presentation.VideoView.playerControls

import androidx.compose.runtime.Composable

@Composable
fun PrevNextPausePlayButtons(
    playerState: Int?,
    visible: Boolean,
    isFirstEpisode: Boolean,
    isLastEpisode: Boolean,
    alpha: Float,
    isVideoPlaying: Boolean,
    onPauseToggle: () -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
    changeVisibleState: () -> Unit,
) {



}