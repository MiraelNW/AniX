package com.miraelDev.anix.presentation.VideoView

import android.content.pm.ActivityInfo
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.miraelDev.anix.domain.models.PlayerWrapper
import com.miraelDev.anix.presentation.VideoView.utilis.setScreenOrientation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PLAYER_SEEK_BACK_INCREMENT = 5 * 1000L // 5 seconds
private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L // 10 seconds

@Composable
fun VideoView(
    modifier: Modifier = Modifier,
    playerWrapper: PlayerWrapper,
//    isFullScreen: Boolean,
    onTrailerChange: ((Int) -> Unit)? = null,
    onFullScreenToggle: (isFullScreen: Boolean) -> Unit,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current

    val viewModel = hiltViewModel<VideoViewModel>()

    val exoPlayer = remember { viewModel.exoPlayer }

//    val exoPlayer = remember {
//        ExoPlayer.Builder(context)
//            .apply {
//                setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
//                setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)
//            }
//            .build()
//            .apply {
//                setMediaItem(
//                    MediaItem.Builder()
//                        .apply {
//                            setUri(
//                                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
//                            )
//                            setMediaMetadata(
//                                MediaMetadata.Builder()
//                                    .setDisplayTitle("My Video")
//                                    .build()
//                            )
//                        }
//                        .build()
//                )
//                prepare()
//                playWhenReady = true
//            }
//    }

    val scope = rememberCoroutineScope()

    var shouldShowControls by remember { mutableStateOf(false) }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var isFullScreen by remember { mutableStateOf(true) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    BackHandler {
        if (isFullScreen) {
            isFullScreen = false
//            onFullScreenToggle(false)
        } else {
            navigateBack()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        DisposableEffect(key1 = Unit) {
            val listener =
                object : Player.Listener {
                    override fun onEvents(
                        player: Player,
                        events: Player.Events
                    ) {
                        super.onEvents(player, events)
                        totalDuration = player.duration.coerceAtLeast(0L)
                        currentTime = player.currentPosition.coerceAtLeast(0L)
                        bufferedPercentage = player.bufferedPercentage
                        isPlaying = player.isPlaying
                        playbackState = player.playbackState

                    }
                }

            exoPlayer.addListener(listener)

            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }

        var timeOut = Job() as Job

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    shouldShowControls = shouldShowControls.not()
                    if (shouldShowControls) {
                        timeOut = scope.launch {
                            delay(3000)
                            shouldShowControls = false
                        }
                    } else {
                        timeOut.cancel()
                    }
                },
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer

                    with(context) {

//                        if (isFullScreen) {
//                            setScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//                        } else {
//                            setScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                        }

                    }

                    useController = false
                    layoutParams =
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                }
            },
            update = {

            }
        )

        PlayerControls(
            modifier = Modifier.fillMaxSize(),
            isVisible = { shouldShowControls },
            isPlaying = { isPlaying },
            title = { exoPlayer.mediaMetadata.displayTitle.toString() },
            playbackState = { playbackState },
            onReplayClick = { exoPlayer.seekBack() },
            onForwardClick = { exoPlayer.seekForward() },
            onPauseToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        // pause the video
                        exoPlayer.pause()
                    }

                    exoPlayer.isPlaying.not() &&
                            playbackState == STATE_ENDED -> {
                        exoPlayer.seekTo(0)
                        exoPlayer.playWhenReady = true
                    }

                    else -> {
                        // play the video
                        // it's already paused
                        exoPlayer.play()
                    }
                }
                isPlaying = isPlaying.not()
            },
            totalDuration = { totalDuration },
            currentTime = { currentTime },
            bufferedPercentage = { bufferedPercentage },
            onSeekChanged = { timeMs: Float ->
                exoPlayer.seekTo(timeMs.toLong())
            },
            onChangeScreenConfig = {
                isFullScreen = !isFullScreen
            }
        )
    }
}


