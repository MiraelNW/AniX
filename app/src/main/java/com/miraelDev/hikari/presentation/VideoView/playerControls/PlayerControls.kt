package com.miraelDev.hikari.presentation.VideoView.playerControls


import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miraelDev.hikari.entensions.noRippleEffectClick
import com.miraelDev.hikari.presentation.VideoView.DropItem

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    isFirstEpisode: Boolean,
    isLastEpisode: Boolean,
    isFullScreen: Int,
    orientation: Int,
    alpha: Float,
    title: () -> String,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    currTime: String,
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

    val visible = remember(isVisible()) {
        true
    }

    var quality by rememberSaveable {
        mutableStateOf("480")
    }

    var shouldShowEpisodeList by rememberSaveable() {
        mutableStateOf(false)
    }

    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

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
                        .background(Color.Black.copy(alpha = 0.3f))
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
                            onEpisodeIconClick()
                        },
                        onAutoLoadNextVideoClick = onAutoNextVideoClick
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
                        currTime = currTime,
                        totalDuration = totalDuration,
                        currentTime = currentTime,
                        isFullScreen = isFullScreen,
                        bufferedPercentage = bufferedPercentage,
                        onSeekChanged = onSeekChanged,
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



        CenterControls(
            modifier = modifier
                .fillMaxHeight(0.5f)
                .align(Alignment.Center),

            isPlaying = isPlaying,
            onReplayClick = onReplayClick,
            onForwardClick = onForwardClick,
            playbackState = playbackState,
            changeVisibleState = changeVisibleState
        )

        PrevNextPausePlayButtons(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                        Alignment.BottomCenter
                    else
                        Alignment.Center
                )
                .padding(bottom = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 16.dp else 0.dp),
            alpha = alpha,
            visible = visible,
            isFirstEpisode = isFirstEpisode,
            isLastEpisode = isLastEpisode,
            playerState = playerState,
            isVideoPlaying = isVideoPlaying,
            onPauseToggle = onPauseToggle,
            onNextVideoClick = onNextVideoClick,
            onPreviousVideoClick = onPreviousVideoClick,
            changeVisibleState = changeVisibleState,
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





