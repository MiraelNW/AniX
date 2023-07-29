package com.miraelDev.hikari.presentation.VideoView.playerControls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.miraelDev.hikari.R
import com.miraelDev.hikari.entensions.noRippleEffectClick
import com.miraelDev.hikari.ui.theme.DirtyWhite
import com.miraelDev.hikari.ui.theme.LightGreen

@Composable
fun PrevNextPausePlayButtons(
    modifier: Modifier,
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
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .noRippleEffectClick(
                    interactionSource = MutableInteractionSource(),
                    enabled = !isFirstEpisode
                ) {
                    if (visible) {
                        onPreviousVideoClick()
                    }
                    changeVisibleState()
                }
                .size(32.dp)
                .weight(1f),
            tint =if(isFirstEpisode) DirtyWhite.copy(alpha = alpha)
            else DirtyWhite.copy(alpha = alpha),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_previous_video),
            contentDescription = "next video"
        )

        Icon(
            modifier = Modifier
                .noRippleEffectClick(MutableInteractionSource()) {
                    onPauseToggle()
                    changeVisibleState()
                }
                .size(42.dp)
                .weight(1f),
            tint = DirtyWhite.copy(alpha = alpha),
            painter =
            when {
                isVideoPlaying -> {
                    painterResource(id = R.drawable.ic_pause)
                }

                isVideoPlaying.not() && playerState == Player.STATE_ENDED -> {
                    painterResource(id = R.drawable.ic_replay)
                }

                else -> {
                    painterResource(id = R.drawable.ic_play)
                }
            },
            contentDescription = "Play/Pause"
        )

        Icon(
            modifier = Modifier
                .noRippleEffectClick(
                    interactionSource =  MutableInteractionSource(),
                    enabled = !isLastEpisode
                ) {
                    if (visible) {
                        onNextVideoClick()
                    }
                    changeVisibleState()
                }
                .size(32.dp)
                .weight(1f),
            tint = if(isLastEpisode) LightGreen.copy(alpha = alpha)
            else DirtyWhite.copy(alpha = alpha),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_next_video),
            contentDescription = "next video"
        )

    }
}