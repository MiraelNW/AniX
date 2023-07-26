package com.miraelDev.hikari.presentation.VideoView

import android.content.res.Configuration
import android.os.CountDownTimer
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.entensions.noRippleEffectClick
import com.miraelDev.hikari.presentation.VideoView.utilis.setAutoOrientation
import com.miraelDev.hikari.presentation.VideoView.utilis.setLandscape
import com.miraelDev.hikari.presentation.VideoView.utilis.setPortrait
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@UnstableApi
@Composable
fun VideoView(
    modifier: Modifier = Modifier,
    animeId: Int,
    videoId: Int,
    onFullScreenToggle: (Int) -> Unit,
    navigateBack: () -> Unit,
    landscape: Int
) {

    val context = LocalContext.current

    val viewModel = hiltViewModel<VideoViewModel>()

    SideEffect {
        viewModel.getAnimeDetail(animeId)
        viewModel.setUrl(videoId)
    }

    val exoPlayer = viewModel.player

    var shouldShowControls by remember { mutableStateOf(false) }

    var isPlaying by rememberSaveable { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    var onToggleButtonCLick by rememberSaveable { mutableStateOf(false) }

    var alphaValue by remember {
        mutableStateOf(0f)
    }

    val alpha by animateFloatAsState(targetValue = alphaValue)

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

    LaunchedEffect(key1 = shouldShowControls) {
        if (shouldShowControls) {
            alphaValue = 1f
            delay(3000)
            shouldShowControls = false
            alphaValue = 0f
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
            update = {

                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                        isPlaying = false
                    }

                    else -> Unit
                }
            }
        )

        PlayerControls(
            modifier = Modifier.fillMaxSize(),
            isVisible = { shouldShowControls },
            isPlaying = { isPlaying },
            isFullScreen = landscape,
            orientation = orientation,
            alpha = alpha,
            title = { exoPlayer.mediaMetadata.displayTitle.toString() },
            playbackState = { playbackState },
            onReplayClick = { exoPlayer.seekBack() },
            onForwardClick = { exoPlayer.seekForward() },
            onPauseToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        exoPlayer.pause()
                    }

                    exoPlayer.isPlaying.not() &&
                            exoPlayer.playbackState == STATE_ENDED -> {
                        exoPlayer.seekTo(0)
                        exoPlayer.playWhenReady = true
                    }

                    else -> {
                        exoPlayer.play()
                    }
                }
                isPlaying = isPlaying.not()
            },
            totalDuration = { totalDuration },
            currentTime = { currentTime },
//            currTime = currTime,
            bufferedPercentage = { bufferedPercentage },
            onSeekChanged = { timeMs: Float ->
                exoPlayer.seekTo(timeMs.toLong())
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
            onNextVideoClick = { viewModel.loadNextVideo() },
            onPreviousVideoClick = { viewModel.loadPreviousVideo() },
            onEpisodeItemClick = { episodeId ->
                viewModel.loadSpecificEpisode(episodeId)
            },
            changeVisibleState = {
                shouldShowControls = shouldShowControls.not()
            },
            onMenuItemClick = { qualityItem ->
                viewModel.loadVideoSelectedQuality( qualityItem.text)
            }
        )
    }
}


