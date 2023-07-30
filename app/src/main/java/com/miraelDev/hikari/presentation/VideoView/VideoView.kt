package com.miraelDev.hikari.presentation.VideoView

import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.miraelDev.hikari.entensions.noRippleEffectClick
import com.miraelDev.hikari.presentation.VideoView.playerControls.PlayerControls
import com.miraelDev.hikari.presentation.VideoView.utilis.formatMinSec
import com.miraelDev.hikari.presentation.VideoView.utilis.setAutoOrientation
import com.miraelDev.hikari.presentation.VideoView.utilis.setLandscape
import com.miraelDev.hikari.presentation.VideoView.utilis.setPortrait
import kotlinx.coroutines.delay

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@UnstableApi
@Composable
fun VideoView(
    modifier: Modifier,
    onFullScreenToggle: (Int) -> Unit,
    navigateBack: () -> Unit,
    landscape: Int
) {


    val context = LocalContext.current

    val viewModel = hiltViewModel<VideoViewModel>()

    val exoPlayer = remember { viewModel.player }

    val isFirstEpisode by viewModel.isFirstEpisode.collectAsState()

    val isLastEpisode by viewModel.isLastEpisode.collectAsState()

    var shouldShowControls by remember { mutableStateOf(false) }

    var clickOnPlayerControls by remember { mutableStateOf(false) }

    var autoLoadNextVideo by remember { mutableStateOf(false) }

    var isPlaying by rememberSaveable { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    val currTime by viewModel.currTime.collectAsState()

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    var onToggleButtonCLick by rememberSaveable { mutableStateOf(false) }

    val title = remember { exoPlayer.mediaMetadata.displayTitle.toString() }

    var alphaValue by remember { mutableStateOf(1f) }

    val alpha by animateFloatAsState(targetValue = alphaValue, label = "prevNext buttons animation")

    val stopTimer: () -> Unit = remember { { viewModel.stopTimer() } }

    val startTimer: () -> Unit = remember { { viewModel.startTimer() } }

    val loadNextVideo: () -> Unit = remember { { viewModel.loadNextVideo() } }

    val loadPreviousVideo: () -> Unit = remember { { viewModel.loadPreviousVideo() } }

    val loadVideoSelectedQuality: (String) -> Unit =
        remember { { viewModel.loadVideoSelectedQuality(it) } }

    val seekToChangeCurrTime: (String) -> Unit =
        remember { { viewModel.seekToChangeCurrTime(it) } }

    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    SideEffect {
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                onFullScreenToggle(LANDSCAPE)
            }

            else -> {
                onFullScreenToggle(PORTRAIT)
            }
        }
    }


    BackHandler {
        if (landscape == LANDSCAPE) {
            context.setPortrait()
            onFullScreenToggle(PORTRAIT)
        } else {
            context.setAutoOrientation()
            exoPlayer.pause()
            navigateBack()
        }
    }

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (onToggleButtonCLick) {
        LaunchedEffect(key1 = Unit) {
            delay(8000)
            context.setAutoOrientation()
            onToggleButtonCLick = false
        }
    }

    LaunchedEffect(key1 = shouldShowControls, key2 = clickOnPlayerControls) {
        if (shouldShowControls && !clickOnPlayerControls) {
            alphaValue = 1f
            delay(3000)
            shouldShowControls = false
            alphaValue = 0f
        } else if (clickOnPlayerControls) {
            alphaValue = 1f
            shouldShowControls = true
        } else {
            alphaValue = 0f
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

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
            }
        }

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .noRippleEffectClick(MutableInteractionSource()) {},
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams =
                        FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                }
            },
//            update = {
//                when (lifecycle) {
//                    Lifecycle.Event.ON_PAUSE -> {
//                        it.onPause()
//                        it.player?.pause()
//                        isPlaying = false
//                    }
//
//                    else -> Unit
//                }
//            }
        )

        LaunchedEffect(key1 = playbackState) {
            if (autoLoadNextVideo && playbackState == STATE_ENDED) {
                stopTimer()
                loadNextVideo()
                startTimer()
            }
        }


        PlayerControls(
            modifier = Modifier.fillMaxSize(),
            isVisible = shouldShowControls,
            isPlaying = { isPlaying },
            isFullScreen = landscape,
            orientation = orientation,
            isFirstEpisode = isFirstEpisode,
            isLastEpisode = isLastEpisode,
            alpha = alpha,
            title = title,
            playbackState = { playbackState },
            onReplayClick = { exoPlayer.seekBack() },
            onForwardClick = { exoPlayer.seekForward() },
            onPauseToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        exoPlayer.pause()
                        stopTimer()
                        clickOnPlayerControls = true
                    }

                    exoPlayer.isPlaying.not() &&
                            exoPlayer.playbackState == STATE_ENDED -> {

                        exoPlayer.seekTo(0)
                        exoPlayer.playWhenReady = true
                    }

                    else -> {
                        exoPlayer.play()
//                        startTimer()
                        clickOnPlayerControls = false
                    }
                }
                isPlaying = isPlaying.not()
            },
            totalDuration = { totalDuration },
            currentTime = { currentTime },
            currTime = currTime,
            bufferedPercentage = { bufferedPercentage },
            onSeekChanged = { timeMs: Float ->
                exoPlayer.seekTo(timeMs.toLong())
                seekToChangeCurrTime(timeMs.formatMinSec())
                clickOnPlayerControls = true
            },
            onValueChangeFinished = {
                clickOnPlayerControls = false
            },
            onFullScreenToggle = { orientation ->

                onToggleButtonCLick = true
                onFullScreenToggle(orientation)
                if (orientation == LANDSCAPE) {
                    context.setLandscape()
                } else {
                    context.setPortrait()
                }

            },
            onBackIconClick = {
                context.setAutoOrientation()
                navigateBack()
            },
            onNextVideoClick = {
                stopTimer()
                loadNextVideo()
                startTimer()
            },
            onPreviousVideoClick = {
                stopTimer()
                loadPreviousVideo()
                startTimer()
            },
            onEpisodeIconClick = {
                clickOnPlayerControls = true
            },
            onCloseEpisodeList = {
                clickOnPlayerControls = false
            },
            onEpisodeItemClick = viewModel::loadSpecificEpisode,
            changeVisibleState = {
                shouldShowControls = !shouldShowControls
            },
            onMenuItemClick = { qualityItem ->
                clickOnPlayerControls = false
                loadVideoSelectedQuality(qualityItem.text)
            },
            onOpenQualityMenu = {
                clickOnPlayerControls = !clickOnPlayerControls
            },
            onAutoNextVideoClick = { autoLoadVideo ->
                autoLoadNextVideo = autoLoadVideo
            }
        )
    }
}


