package com.miraelDev.vauma.presentation.videoView.playerControls


import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import com.miraelDev.vauma.R
import com.miraelDev.vauma.exntensions.noRippleEffectClick
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation
import com.miraelDev.vauma.presentation.videoView.DropItem
import com.miraelDev.vauma.ui.theme.DirtyWhite
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier,
    isVisible: Boolean,
    isPlaying: () -> Boolean,
    isFirstEpisode: Boolean,
    isLastEpisode: Boolean,
    orientation: Int,
    title: String,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,
    totalDuration: () -> Long,
    currentTime: Long,
    bufferedPercentage: () -> Int,
    onBackIconClick: () -> Unit,
    playbackState: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    onFullScreenToggle: (Int) -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    onEpisodeIconClick: () -> Unit,
    onCloseEpisodeList: () -> Unit,
    changeVisibleState: () -> Unit,
    onMenuItemClick: (DropItem) -> Unit,
    onOpenQualityMenu: () -> Unit,
    onAutoNextVideoClick: (Boolean) -> Unit,
) {


    val visible = remember(isVisible) { true }

    var quality by rememberSaveable {
        mutableStateOf("480")
    }

    var shouldShowEpisodeList by rememberSaveable() {
        mutableStateOf(false)
    }

    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    var videoTime by rememberSaveable { mutableLongStateOf(0L) }

    val onBackIconClickSaved: () -> Unit = remember { { onBackIconClick() } }

    val onEpisodeIconClickSaved: () -> Unit = remember { { onEpisodeIconClick() } }

    val onAutoNextVideoClickSaved: (Boolean) -> Unit = remember { { onAutoNextVideoClick(it) } }

    LaunchedEffect(key1 = videoTime, key2 = isVideoPlaying) {
        if (isVideoPlaying) {
            delay(1000)
            videoTime += 1000
        }
        if (playerState == STATE_ENDED) {
            videoTime = totalDuration()
        }
    }

    Box(modifier = modifier) {

        Box(modifier = modifier.noRippleEffectClick { changeVisibleState() }) {

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
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {

                    TopControl(
                        title = title,
                        onBackIconClick = onBackIconClickSaved,
                        onEpisodeIconClick = {
                            shouldShowEpisodeList = true
                            onEpisodeIconClickSaved()
                        },
                        onAutoLoadNextVideoClick = onAutoNextVideoClickSaved
                    )

                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        BottomControls(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .navigationBarsPadding()
                                .padding(start = 24.dp)
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
                            currentTime = videoTime,
                            isFullScreen = orientation,
                            bufferedPercentage = bufferedPercentage,
                            onSeekChanged = {
                                videoTime = it.toLong()
                                onSeekChanged(it)
                            },
                            onFullScreenToggle = onFullScreenToggle,
                            onValueChangeFinished = onValueChangeFinished,
                            onMenuItemClick = { dropItem ->
                                quality = dropItem.text
                                onMenuItemClick(dropItem)
                            },
                            onOpenQualityMenu = onOpenQualityMenu
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)

                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                modifier = Modifier
                                    .noRippleEffectClick(
                                        enabled = !isFirstEpisode,
                                        onClick = {
                                            videoTime = 0
                                            onPreviousVideoClick()
                                        }
                                    )
                                    .size(32.dp)
                                    .weight(1f),
                                tint = if (isFirstEpisode) DirtyWhite.copy(alpha = 0.4f) else DirtyWhite,
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_previous_video),
                                contentDescription = "next video"
                            )

                            Icon(
                                modifier = Modifier
                                    .size(42.dp)
                                    .noRippleEffectClick(
                                        onClick = {
                                            if (isVideoPlaying.not() && playerState == Player.STATE_ENDED) {
                                                videoTime = 0
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

                            Icon(
                                modifier = Modifier
                                    .noRippleEffectClick(
                                        enabled = !isLastEpisode,
                                        onClick = {
                                            videoTime = 0
                                            onNextVideoClick()
                                        }
                                    )
                                    .size(32.dp)
                                    .weight(1f),
                                tint = if (isLastEpisode) DirtyWhite.copy(alpha = 0.4f) else DirtyWhite,
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_next_video),
                                contentDescription = "next video"
                            )


                        }
                    } else {
                        PortraitBottomControls(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .navigationBarsPadding()
                                .padding(
                                    start = 8.dp
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
                            isVideoPlaying = isVideoPlaying,
                            playerState = playerState,
                            quality = quality,
                            resetTime = { videoTime = 0 },
                            onPauseToggle = onPauseToggle,
                            totalDuration = totalDuration,
                            currentTime = videoTime,
                            isFullScreen = orientation,
                            bufferedPercentage = bufferedPercentage,
                            onSeekChanged = {
                                videoTime = it.toLong()
                                onSeekChanged(it)
                            },
                            onFullScreenToggle = onFullScreenToggle,
                            onValueChangeFinished = onValueChangeFinished,
                            onMenuItemClick = { dropItem ->
                                quality = dropItem.text
                                onMenuItemClick(dropItem)
                            },
                            onOpenQualityMenu = onOpenQualityMenu
                        )
                    }


                }
            }
        }

        CenterControls(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.Center),
            onReplayClick = {
                if (videoTime - 10000 > 0) {
                    videoTime -= 10000
                } else {
                    videoTime = 0
                }
                onReplayClick()
            },
            onForwardClick = {
                if (videoTime + 10000 < totalDuration()) {
                    videoTime += 10000
                } else {
                    videoTime = totalDuration()
                }
                onForwardClick()
            },
            changeVisibleState = changeVisibleState
        )


        EpisodeList(
            modifier = modifier,
            shouldShowEpisodeList = shouldShowEpisodeList,
            orientation = orientation,
            onCloseEpisodeList = {
                shouldShowEpisodeList = false
                onCloseEpisodeList()
            },
            onEpisodeItemClick = { episodeId ->
                onEpisodeItemClick(episodeId)
                onCloseEpisodeList()
            }
        )
    }
}





