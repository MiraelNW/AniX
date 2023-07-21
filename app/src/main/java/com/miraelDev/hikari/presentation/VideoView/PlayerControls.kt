package com.miraelDev.hikari.presentation.VideoView


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player.STATE_ENDED
import com.miraelDev.hikari.R
import com.miraelDev.hikari.presentation.VideoView.utilis.formatMinSec


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    isFullScreen: Boolean,
    title: () -> String,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,
    totalDuration: () -> Long,
    currentTime: () -> Long,
//    currTime: String,
    bufferedPercentage: () -> Int,
    onBackIconClick: () -> Unit,
    playbackState: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    onFullScreenToggle: (Boolean) -> Unit
) {
    val visible = remember(isVisible()) { isVisible() }

    var quality by rememberSaveable {
        mutableStateOf("480")
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            TopControl(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .statusBarsPadding(),
                title = title,
                onBackIconClick = onBackIconClick
            )

            CenterControls(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                isPlaying = isPlaying,
                onReplayClick = onReplayClick,
                onForwardClick = onForwardClick,
                onPauseToggle = onPauseToggle,
                playbackState = playbackState
            )

            BottomControls(
                modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .animateEnterExit(
                        enter =
                        slideInVertically(
                            initialOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        ),
                        exit =
                        slideOutVertically(
                            targetOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        )
                    ),
                quality = quality,
                totalDuration = totalDuration,
                currentTime = currentTime,
                isFullScreen = isFullScreen,
//                currTime = currTime,
                bufferedPercentage = bufferedPercentage,
                onSeekChanged = onSeekChanged,
                onFullScreenToggle = onFullScreenToggle,
                onMenuItemClick = { dropItem ->
                    quality = dropItem.text
                }
            )
        }
    }
}

@Composable
private fun TopControl(
    modifier: Modifier = Modifier,
    title: () -> String,
    onBackIconClick: () -> Unit
) {
    val videoTitle = remember(title()) { title() }

    Row(
        modifier = modifier.padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onBackIconClick
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "back",
                tint = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = videoTitle,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
    }


}

@Composable
private fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onReplayClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onForwardClick: () -> Unit
) {
    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(modifier = Modifier.size(40.dp), onClick = onReplayClick) {
            Icon(
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colors.primary,
                painter = painterResource(id = R.drawable.ic_back_10_sec),
                contentDescription = "Replay 5 seconds"
            )
        }

        IconButton(modifier = Modifier.size(40.dp), onClick = onPauseToggle) {
            Icon(
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colors.primary,
                painter =
                when {
                    isVideoPlaying -> {
                        painterResource(id = R.drawable.ic_pause)
                    }

                    isVideoPlaying.not() && playerState == STATE_ENDED -> {
                        painterResource(id = R.drawable.ic_replay)
                    }

                    else -> {
                        painterResource(id = R.drawable.ic_play)
                    }
                },
                contentDescription = "Play/Pause"
            )
        }

        IconButton(modifier = Modifier.size(40.dp), onClick = onForwardClick) {
            Icon(
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colors.primary,
                painter = painterResource(id = R.drawable.ic_forward_10),
                contentDescription = "Forward 10 seconds"
            )
        }
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier = Modifier,
    quality: String,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    isFullScreen: Boolean,
//    currTime: String,
    bufferedPercentage: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    onFullScreenToggle: (Boolean) -> Unit,
    onMenuItemClick: (DropItem) -> Unit
) {

    val duration = remember(totalDuration()) { totalDuration() }

    val videoTime = remember(currentTime()) { currentTime() }

    val buffer = remember(bufferedPercentage()) { bufferedPercentage() }

    Column(modifier = modifier.padding(bottom = 16.dp, start = 8.dp, end = 8.dp)) {
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
                value = videoTime.toFloat(),
                onValueChange = onSeekChanged,
                valueRange = 0f..duration.toFloat(),
                colors =
                SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.primary,
                    activeTickColor = MaterialTheme.colors.primary
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TimeLineRow(duration = duration)

            QualityButtonWithFullScreenButton(
                isFullScreen = isFullScreen,
                onFullScreenToggle = onFullScreenToggle,
                quality = quality,
                onMenuItemClick = onMenuItemClick
            )

        }
    }
}

@Composable
private fun QualityButtonWithFullScreenButton(
    quality: String,
    isFullScreen: Boolean,
    onFullScreenToggle: (Boolean) -> Unit,
    onMenuItemClick: (DropItem) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        QualityItems(
            quality = quality,
            dropdownItems = listOf(
                DropItem("480"),
                DropItem("720"),
                DropItem("1080")
            ),
            onMenuItemClick = onMenuItemClick
        )
        IconButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                onFullScreenToggle(isFullScreen.not())
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_fullscreen),
                contentDescription = "Enter/Exit fullscreen",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun TimeLineRow(duration: Long) {
    Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    text = currTime,
//                    color = MaterialTheme.colors.primary
//                )
        Text(
            text = "/",
            color = MaterialTheme.colors.primary
        )
        Text(
            text = duration.formatMinSec(),
            color = MaterialTheme.colors.primary
        )
    }
}


