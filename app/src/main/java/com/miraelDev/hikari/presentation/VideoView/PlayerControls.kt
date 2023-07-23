package com.miraelDev.hikari.presentation.VideoView


import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.media3.common.Player.STATE_ENDED
import com.miraelDev.hikari.R
import com.miraelDev.hikari.presentation.VideoView.utilis.formatMinSec
import kotlinx.coroutines.delay

private const val PORTRAIT = 0
private const val LANDSCAPE = 1
private const val USER_ORIENTATION = 2

@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    isFullScreen: Int,
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
    onFullScreenToggle: (Int) -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
) {

    var backVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val visible = remember(isVisible()) { true }

    var quality by rememberSaveable {
        mutableStateOf("480")
    }
    Box(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .align(Alignment.CenterStart)
            .combinedClickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { Log.d("tag", "one click") },
                onDoubleClick = {
                    onReplayClick()
                    backVisible = true
                },
                onLongClick = {}
            )
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = backVisible,
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_player_back),
                    contentDescription = "player back icon",
                    tint = MaterialTheme.colors.primary
                )
                LaunchedEffect(key1 = backVisible) {
                    delay(350)
                    backVisible = false
                }
            }
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.6f))
            ) {

                TopControl(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(
                            top = if (isFullScreen == LANDSCAPE) 16.dp else 8.dp,
                            start = if (isFullScreen == LANDSCAPE) 8.dp else 16.dp,
                        )
                        .align(Alignment.TopStart)
                        .fillMaxWidth(),
                    title = title,
                    onBackIconClick = {
                        onBackIconClick()
                    },
                    onEpisodeIconClick = { }
                )

                CenterControls(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    isPlaying = isPlaying,
                    onReplayClick = onReplayClick,
                    onForwardClick = onForwardClick,
                    onPauseToggle = onPauseToggle,
                    playbackState = playbackState,
                    onNextVideoClick = onNextVideoClick,
                    onPreviousVideoClick = onPreviousVideoClick,
                )

                BottomControls(
                    modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(
                            start = if (isFullScreen == LANDSCAPE) 24.dp else 8.dp
                        )
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
}

@Composable
private fun TopControl(
    modifier: Modifier = Modifier,
    title: () -> String,
    onBackIconClick: () -> Unit,
    onEpisodeIconClick: () -> Unit,
) {
    val videoTitle = remember(title()) { title() }

    Row(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
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
        EpisodesItems(
            dropdownItems = listOf(
                EpisodeDropItem("ep1212121212121"),
                EpisodeDropItem("ep2212121212"),
                EpisodeDropItem("ep212121212121"),
                EpisodeDropItem("ep212121212121"),
                EpisodeDropItem("ep221212121212"),
                EpisodeDropItem("ep221212"),
                EpisodeDropItem("ep2212121212"),
                EpisodeDropItem("ep212121212"),
                EpisodeDropItem("ep212121212"),
                EpisodeDropItem("ep212121212"),

                ),
            onMenuItemClick = {},
            onEpisodeIconClick = onEpisodeIconClick
        )
//        IconButton(
//            modifier = Modifier
//                .size(44.dp)
//                .padding(end = 16.dp),
//            onClick = {}
//        ) {
//            Icon(
//                modifier = Modifier.fillMaxSize(),
//                imageVector = ImageVector.vectorResource(id = R.drawable.ic_episodes),
//                contentDescription = "episodes",
//                tint = MaterialTheme.colors.primary
//            )
//        }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onReplayClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onForwardClick: () -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
) {

    var forwardVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {


        PrevNextPausePlayButtons(
            modifier = Modifier.weight(1f),
            playerState = playerState,
            isVideoPlaying = isVideoPlaying,
            onPauseToggle = onPauseToggle,
            onNextVideoClick = onNextVideoClick,
            onPreviousVideoClick = onPreviousVideoClick
        )

//        Box(modifier = Modifier
//            .fillMaxHeight()
//            .weight(1f)
//            .pointerInput(MutableInteractionSource()) {
//                detectTapGestures(
//                    onDoubleTap = {
//                        onForwardClick()
//                        forwardVisible = true
//                    },
//                )
//            }
//        ) {
//
//            androidx.compose.animation.AnimatedVisibility(
//                modifier = Modifier.align(Alignment.Center),
//                visible = forwardVisible,
//                exit = fadeOut()
//            ) {
//                Icon(
//                    modifier = Modifier.size(48.dp),
//                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_player_forward),
//                    contentDescription = "player forward icon",
//                    tint = MaterialTheme.colors.primary
//                )
//                LaunchedEffect(key1 = forwardVisible) {
//                    delay(350)
//                    forwardVisible = false
//                }
//            }
//        }
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier,
    quality: String,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    isFullScreen: Int,
//    currTime: String,
    bufferedPercentage: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    onFullScreenToggle: (Int) -> Unit,
    onMenuItemClick: (DropItem) -> Unit
) {

    val duration = remember(totalDuration()) { totalDuration() }

    val videoTime = remember(currentTime()) { currentTime() }

    val buffer = remember(bufferedPercentage()) { bufferedPercentage() }

    Column(modifier = modifier.padding(bottom = 16.dp, start = 24.dp, end = 16.dp)) {
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
                .fillMaxWidth(),
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
    isFullScreen: Int,
    onFullScreenToggle: (Int) -> Unit,
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
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .size(24.dp),
            onClick = {
                onFullScreenToggle(if (isFullScreen == LANDSCAPE) PORTRAIT else LANDSCAPE)
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
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

@Composable
private fun PrevNextPausePlayButtons(
    modifier: Modifier,
    playerState: Int,
    isVideoPlaying: Boolean,
    onPauseToggle: () -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            modifier = Modifier
                .size(36.dp)
                .weight(1f), onClick = onPreviousVideoClick
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colors.primary,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_previous_video),
                contentDescription = "next video"
            )
        }

        IconButton(
            modifier = Modifier
                .size(48.dp)
                .weight(1f), onClick = onPauseToggle
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
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

        IconButton(
            modifier = Modifier
                .size(36.dp)
                .weight(1f), onClick = onNextVideoClick
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colors.primary,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_next_video),
                contentDescription = "next video"
            )
        }
    }
}




