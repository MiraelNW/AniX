package com.miraelDev.vauma.presentation.videoView.playerControls

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.google.common.collect.ImmutableList
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.videoView.DropItem
import com.miraelDev.vauma.presentation.videoView.QualityItems
import com.miraelDev.vauma.presentation.videoView.utilis.formatMinSec
import com.miraelDev.vauma.ui.theme.DirtyWhite

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@Composable
fun PortraitBottomControls(
        modifier: Modifier,
        quality: String,
        totalDuration: () -> Long,
        currentTime: Long,
        isFullScreen: Int,
        isVideoPlaying: Boolean,
        playerState: Int,
        bufferedPercentage: () -> Int,
        onValueChangeFinished: () -> Unit,
        resetTime: () -> Unit,
        onPauseToggle: () -> Unit,
        onSeekChanged: (timeMs: Float) -> Unit,
        onFullScreenToggle: (Int) -> Unit,
        onMenuItemClick: (DropItem) -> Unit,
        onOpenQualityMenu: () -> Unit,
) {

    val duration = remember(totalDuration()) { totalDuration() }

    val buffer = remember(bufferedPercentage()) { bufferedPercentage() }

    val onFullScreenToggleSaved: (Int) -> Unit = remember { { onFullScreenToggle(it) } }

    Column(
            modifier = modifier
                    .padding(bottom = 16.dp, start = 24.dp, end = 16.dp)
                    .navigationBarsPadding()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Slider(
                    value = buffer.toFloat(),
                    enabled = false,
                    onValueChange = { /*do nothing*/ },
                    valueRange = 0f..100f,
                    colors =
                    SliderDefaults.colors(
                            disabledThumbColor = Color.Transparent,
                            disabledActiveTrackColor = Color.Gray
                    )
            )

            Slider(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentTime.toFloat(),
                    onValueChange = onSeekChanged,
                    onValueChangeFinished = onValueChangeFinished,
                    valueRange = 0f..duration.toFloat(),
                    colors =
                    SliderDefaults.colors(
                            thumbColor = MaterialTheme.colors.primary,
                            inactiveTrackColor = DirtyWhite.copy(alpha = 0.5f),
                            activeTickColor = MaterialTheme.colors.primary
                    )
            )
        }

        Row(
                modifier = Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleEffectClick(
                            enabled = playerState != Player.STATE_BUFFERING ,
                            onClick = {
                                if (isVideoPlaying.not() && playerState == Player.STATE_ENDED) {
                                    resetTime()
                                }
                                onPauseToggle()
                            }
                        ),
                    tint = DirtyWhite,
                    imageVector =
                    when {
                        isVideoPlaying -> {
                            ImageVector.vectorResource(id = R.drawable.ic_pause)
                        }

                        isVideoPlaying.not() && playerState == Player.STATE_ENDED -> {
                            ImageVector.vectorResource(id = R.drawable.ic_replay)
                        }

                        else -> {
                            ImageVector.vectorResource(id = R.drawable.ic_play)
                        }
                    },
                    contentDescription = "Play/pause"
                )

                TimeLineRow(currTime = currentTime.formatMinSec(), duration = duration)
            }

            QualityButtonWithFullScreenButton(
                    isFullScreen = isFullScreen,
                    onFullScreenToggle = onFullScreenToggleSaved,
                    quality = quality,
                    onMenuItemClick = onMenuItemClick,
                    onOpenQualityMenu = onOpenQualityMenu
            )

        }
    }
}

@Composable
private fun QualityButtonWithFullScreenButton(
        quality: String,
        isFullScreen: Int,
        onFullScreenToggle: (Int) -> Unit,
        onMenuItemClick: (DropItem) -> Unit,
        onOpenQualityMenu: () -> Unit,
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        QualityItems(
                quality = quality,
                dropdownItems = ImmutableList.of(
                        DropItem("480"),
                        DropItem("720"),
                        DropItem("1080")
                ),
                onOpenQualityMenu = onOpenQualityMenu,
                onMenuItemClick = onMenuItemClick
        )

        IconButton(
                modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .size(24.dp),
                onClick = {
                    onFullScreenToggle(if (isFullScreen == Configuration.ORIENTATION_LANDSCAPE) PORTRAIT else LANDSCAPE)
                }
        ) {
            Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_fullscreen),
                    contentDescription = "Enter/Exit fullscreen",
                    tint = DirtyWhite
            )
        }
    }
}

@Composable
private fun TimeLineRow(
        duration: Long,
        currTime: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = currTime,
                color = DirtyWhite
        )
        Text(
                text = "/",
                color = DirtyWhite
        )
        Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = duration.formatMinSec(),
                color = DirtyWhite
        )
    }
}
