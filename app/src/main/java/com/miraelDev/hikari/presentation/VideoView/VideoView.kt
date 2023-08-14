package com.miraelDev.hikari.presentation.VideoView

import android.content.res.Configuration
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.miraelDev.hikari.presentation.MainScreen.LocalOrientation
import com.miraelDev.hikari.presentation.VideoView.playerControls.PlayerControls
import com.miraelDev.hikari.presentation.VideoView.utilis.setAutoOrientation
import com.miraelDev.hikari.presentation.VideoView.utilis.setLandscape
import com.miraelDev.hikari.presentation.VideoView.utilis.setPortrait
import kotlinx.coroutines.delay

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@UnstableApi
@Composable
fun VideoViewScreen(
        onFullScreenToggle: (Int) -> Unit,
        navigateBack: () -> Unit,
) {
    val viewModel = hiltViewModel<VideoViewModel>()

    val playerWrapper by viewModel.screenState.collectAsState()

//    when (val res = screenState.value) {
//
//        is VideoViewScreenState.Result -> {
//            val playerWrapper = res.result
    VideoView(
            exoPlayer = playerWrapper.exoPlayer,
            isFirstEpisode = playerWrapper.isFirstEpisode,
            isLastEpisode = playerWrapper.isLastEpisode,
            title = playerWrapper.title,
            onFullScreenToggle = onFullScreenToggle,
            navigateBack = navigateBack,
            onNextVideoClick = viewModel::loadNextVideo,
            onPreviousVideoClick = viewModel::loadPreviousVideo,
            onEpisodeItemClick = viewModel::loadSpecificEpisode,
            loadVideoSelectedQuality = viewModel::loadVideoSelectedQuality
    )
//        }

//        is VideoViewScreenState.Failure -> {
//
//        }
//
//        is VideoViewScreenState.Loading -> {
//
//        }
//
//        is VideoViewScreenState.Initial -> {}
//
//    }
}

@Composable
private fun VideoView(
        exoPlayer: ExoPlayer,
        isFirstEpisode: Boolean,
        isLastEpisode: Boolean,
        title: String,
        onFullScreenToggle: (Int) -> Unit,
        navigateBack: () -> Unit,
        onNextVideoClick: () -> Unit,
        onPreviousVideoClick: () -> Unit,
        onEpisodeItemClick: (Int) -> Unit,
        loadVideoSelectedQuality: (String) -> Unit,
) {
    val context = LocalContext.current

    val landscape = LocalOrientation.current

    var clickOnPlayerControls by remember { mutableStateOf(false) }

    var isControlsVisible by remember { mutableStateOf(false) }

    var autoLoadNextVideo by remember { mutableStateOf(false) }

    var isPlaying by rememberSaveable { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var videoTime by rememberSaveable { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    var onToggleButtonCLick by rememberSaveable { mutableStateOf(false) }

    val onReplayClickSaved: () -> Unit = remember { { exoPlayer.seekBack() } }

    val onForwardClickSaved: () -> Unit = remember { { exoPlayer.seekForward() } }

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

    LaunchedEffect(key1 = isControlsVisible) {
        if (isControlsVisible) {
            delay(3000)
            isControlsVisible = false
        }
    }

    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
    ) {


        VideoPlayer(
                exoPlayer = exoPlayer,
                lifecycle = lifecycle,
                isPlayingClick = { isPlaying = it }
        )

        PlayerControls(
                modifier = Modifier.fillMaxSize(),
                isVisible = isControlsVisible,
                isPlaying = { isPlaying },
                isFullScreen = landscape,
                orientation = orientation,
                isFirstEpisode = isFirstEpisode,
                isLastEpisode = isLastEpisode,
                title = title,
                playbackState = { playbackState },
                onReplayClick = onReplayClickSaved,
                onForwardClick = onForwardClickSaved,
                onPauseToggle = {
                    when {
                        exoPlayer.isPlaying -> {
                            exoPlayer.pause()
                            clickOnPlayerControls = true
                        }

                        exoPlayer.isPlaying.not() &&
                                exoPlayer.playbackState == STATE_ENDED -> {
                            exoPlayer.seekTo(0)
                            videoTime = 0
                            exoPlayer.playWhenReady = true
                        }

                        else -> {
                            exoPlayer.play()
                            clickOnPlayerControls = false
                        }
                    }
                    isPlaying = isPlaying.not()
                },
                totalDuration = { totalDuration },
                currentTime = currentTime,
                bufferedPercentage = { bufferedPercentage },
                onSeekChanged = { timeMs: Float ->
                    exoPlayer.seekTo(timeMs.toLong())
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
                onNextVideoClick = onNextVideoClick,
                onPreviousVideoClick = onPreviousVideoClick,
                onEpisodeIconClick = {
                    clickOnPlayerControls = true
                },
                onCloseEpisodeList = {
                    clickOnPlayerControls = false
                },
                onEpisodeItemClick = onEpisodeItemClick,
                changeVisibleState = {
                    isControlsVisible = !isControlsVisible
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

@Composable
private fun VideoPlayer(
        exoPlayer: ExoPlayer,
        lifecycle: Lifecycle.Event,
        isPlayingClick: (Boolean) -> Unit
) {
    val context = LocalContext.current

    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
    ) {
        AndroidView(
                modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
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
                update = {
                    when (lifecycle) {
                        Lifecycle.Event.ON_PAUSE -> {
                            it.onPause()
                            it.player?.pause()
                            isPlayingClick(false)
                        }

                        else -> Unit
                    }
                }
        )
    }
}


