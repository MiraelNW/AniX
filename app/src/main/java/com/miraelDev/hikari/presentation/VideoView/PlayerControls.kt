package com.miraelDev.hikari.presentation.VideoView


import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player.STATE_ENDED
import com.miraelDev.hikari.R
import com.miraelDev.hikari.entensions.noRippleEffectClick
import com.miraelDev.hikari.presentation.VideoView.utilis.formatMinSec
import com.miraelDev.hikari.ui.theme.DarkGray
import com.miraelDev.hikari.ui.theme.DarkWhite
import kotlinx.coroutines.delay

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    isFullScreen: Int,
    orientation: Int,
    alpha: Float,
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
    onEpisodeItemClick: (Int) -> Unit,
    changeVisibleState: () -> Unit,
    onMenuItemClick: (DropItem) -> Unit,
) {

    val visible = remember(isVisible()) {
        isVisible()
    }


    var quality by rememberSaveable {
        mutableStateOf("480")
    }

    var shouldShowEpisodeList by rememberSaveable() {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {

        Box(modifier = modifier.noRippleEffectClick(MutableInteractionSource()) { changeVisibleState() }) {

            AnimatedVisibility(
                modifier = modifier,
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut(),
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
                            .fillMaxWidth()
                            .padding(
                                top = if (isFullScreen == LANDSCAPE) 16.dp else 8.dp,
                                start = if (isFullScreen == LANDSCAPE) 8.dp else 16.dp,
                            )
                            .align(Alignment.TopStart),
                        title = title,
                        onBackIconClick = onBackIconClick,
                        onEpisodeIconClick = {
                            shouldShowEpisodeList = true
                        },
                        changeVisibleState = changeVisibleState
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
                            onMenuItemClick(dropItem)
                        }
                    )
                }
            }
        }

        CenterControls(
            modifier = modifier
                .fillMaxHeight(0.5f)
                .align(Alignment.Center),
            alpha = alpha,
            visible = visible,
            isPlaying = isPlaying,
            onReplayClick = onReplayClick,
            onForwardClick = onForwardClick,
            onPauseToggle = onPauseToggle,
            playbackState = playbackState,
            onNextVideoClick = onNextVideoClick,
            onPreviousVideoClick = onPreviousVideoClick,
            changeVisibleState = changeVisibleState
        )

        val interactionSource = MutableInteractionSource()

        AnimatedVisibility(
            visible = shouldShowEpisodeList,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = modifier
                    .background(Color.Black.copy(alpha = 0.3f))
                    .noRippleEffectClick(interactionSource = interactionSource) {
                        shouldShowEpisodeList = false
                    }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 1f else 0.45f)
                        .fillMaxWidth(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.35f else 1f)
                        .navigationBarsPadding()
                        .align(
                            if (orientation == Configuration.ORIENTATION_LANDSCAPE) Alignment.CenterEnd else Alignment.BottomCenter
                        )
                        .noRippleEffectClick(MutableInteractionSource()) { },
                    elevation = 0.dp,
                    shape =
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                        RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                    else
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    backgroundColor = DarkGray
                ) {
                    val scrollState = rememberScrollState()
                    val list = listOf(
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                        "dfgdgdfgdfgdfgdfg",
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(top = 16.dp, start = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { shouldShowEpisodeList = false }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Arrow back",
                                    tint = DarkWhite
                                )
                            }
                            Text(text = "Другие эпизоды", fontSize = 24.sp, color = DarkWhite)
                        }
                        Column(
                            modifier = Modifier
                                .padding(
                                    top = 16.dp,
                                    start = 20.dp,
                                    bottom = 16.dp,
                                    end = 20.dp
                                )
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            list.forEachIndexed { index, str ->
                                EpisodeNameItem(
                                    index = index,
                                    name = str,
                                    onEpisodeItemClick = onEpisodeItemClick
                                )
                            }
                        }
                    }

                }

            }
        }

    }
}

@Composable
private fun EpisodeNameItem(
    index: Int,
    name: String,
    onEpisodeItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEpisodeItemClick(index) }
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Ep${index + 1} $name", fontSize = 20.sp, color = DarkWhite)
            Text(text = "22:01", color = DarkWhite.copy(alpha = 0.8f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(startIndent = 8.dp, color = DarkWhite.copy(alpha = 0.8f), thickness = 1.dp)
    }
}


@Composable
private fun TopControl(
    modifier: Modifier = Modifier,
    title: () -> String,
    onBackIconClick: () -> Unit,
    onEpisodeIconClick: () -> Unit,
    changeVisibleState: () -> Unit,
) {
    val videoTitle = remember(title()) { title() }

    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .navigationBarsPadding(),
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

        IconButton(
            modifier = Modifier
                .size(44.dp)
                .padding(end = 16.dp),
            onClick = onEpisodeIconClick
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_episodes),
                contentDescription = "episodes",
                tint = MaterialTheme.colors.primary
            )
        }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CenterControls(
    modifier: Modifier = Modifier,
    alpha: Float,
    visible: Boolean,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onPauseToggle: () -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    changeVisibleState: () -> Unit,
) {

    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    var backVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var forwardVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.5f)
                    .combinedClickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = changeVisibleState,
                        onDoubleClick = {
                            onReplayClick()
                            backVisible = true
                        },
                        onLongClick = {}
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.Start
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = backVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {

                        Icon(
                            modifier = Modifier.size(36.dp),
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
            }

            Box(modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .combinedClickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = changeVisibleState,
                    onDoubleClick = {
                        onForwardClick()
                        forwardVisible = true
                    },
                    onLongClick = {}
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.End
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = forwardVisible,
                        exit = fadeOut()
                    ) {
                        Icon(
                            modifier = Modifier.size(36.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_player_forward),
                            contentDescription = "player forward icon",
                            tint = MaterialTheme.colors.primary
                        )
                        LaunchedEffect(key1 = forwardVisible) {
                            delay(350)
                            forwardVisible = false
                        }
                    }

                }
            }
        }

        PrevNextPausePlayButtons(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.Center),
            alpha = alpha,
            visible = visible,
            playerState = playerState,
            isVideoPlaying = isVideoPlaying,
            onPauseToggle = onPauseToggle,
            onNextVideoClick = onNextVideoClick,
            onPreviousVideoClick = onPreviousVideoClick,
            changeVisibleState = changeVisibleState,
        )
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
fun PrevNextPausePlayButtons(
    modifier: Modifier,
    playerState: Int?,
    visible: Boolean,
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
                .noRippleEffectClick(MutableInteractionSource()) {
                    if (visible) {
                        onPreviousVideoClick()
                    }
                    changeVisibleState()
                }
                .size(36.dp)
                .weight(1f),
            tint = MaterialTheme.colors.primary.copy(alpha = alpha),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_previous_video),
            contentDescription = "next video"
        )

        Icon(
            modifier = Modifier
                .noRippleEffectClick(MutableInteractionSource()) {
                    onPauseToggle()
                    changeVisibleState()
                }
                .size(48.dp)
                .weight(1f),
            tint = MaterialTheme.colors.primary.copy(alpha = alpha),
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

        Icon(
            modifier = Modifier
                .noRippleEffectClick(MutableInteractionSource()) {
                    if (visible) {
                        onNextVideoClick()
                    }
                    changeVisibleState()
                }
                .size(36.dp)
                .weight(1f),
            tint = MaterialTheme.colors.primary.copy(alpha = alpha),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_next_video),
            contentDescription = "next video"
        )

    }
}




