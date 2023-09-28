import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_BUFFERING
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.ui.PlayerView
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation
import com.miraelDev.vauma.presentation.videoView.VideoViewModel
import com.miraelDev.vauma.presentation.videoView.playerControls.PlayerControls
import com.miraelDev.vauma.presentation.videoView.utilis.setAutoOrientation
import com.miraelDev.vauma.presentation.videoView.utilis.setLandscape
import com.miraelDev.vauma.presentation.videoView.utilis.setPortrait
import com.miraelDev.vauma.ui.theme.LightGreen700
import kotlinx.coroutines.delay

private const val PORTRAIT = 0
private const val LANDSCAPE = 1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@UnstableApi
@Composable
fun VideoViewScreen(
    navigateBack: () -> Unit,
) {
    val viewModel: VideoViewModel = hiltViewModel()

    val playerWrapper by viewModel.screenState.collectAsStateWithLifecycle()


//    when (val res = screenState.value) {
//
//        is VideoViewScreenState.Result -> {
//            val playerWrapper = res.result
    VideoView(
        exoPlayer = playerWrapper.exoPlayer,
        isFirstEpisode = playerWrapper.isFirstEpisode,
        isLastEpisode = playerWrapper.isLastEpisode,
        title = playerWrapper.title,
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
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun VideoView(
    exoPlayer: ExoPlayer,
    isFirstEpisode: Boolean,
    isLastEpisode: Boolean,
    title: String,
    navigateBack: () -> Unit,
    onNextVideoClick: () -> Unit,
    onPreviousVideoClick: () -> Unit,
    onEpisodeItemClick: (Int) -> Unit,
    loadVideoSelectedQuality: (String) -> Unit,
) {
    val context = LocalContext.current

    val orientation = LocalOrientation.current

    var clickOnPlayerControls by remember { mutableStateOf(false) }

    var isControlsVisible by remember { mutableStateOf(false) }

    var autoLoadNextVideo by rememberSaveable { mutableStateOf(false) }

    var isPlaying by rememberSaveable { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableLongStateOf(0L) }

    var currentTime by rememberSaveable { mutableLongStateOf(0L) }

    var bufferedPercentage by rememberSaveable { mutableIntStateOf(0) }

    var playbackState by remember { mutableIntStateOf(exoPlayer.playbackState) }

    var onToggleButtonCLick by rememberSaveable { mutableStateOf(false) }

    val onReplayClickSaved: () -> Unit = remember { { exoPlayer.seekBack() } }

    val onForwardClickSaved: () -> Unit = remember { { exoPlayer.seekForward() } }

    val isPlayingClick: (Boolean) -> Unit = remember { { isPlaying = it } }

    BackHandler {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            context.setPortrait()
        } else {
            context.setAutoOrientation()
            navigateBack()
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

    LaunchedEffect(key1 = playbackState == STATE_ENDED) {
        if (autoLoadNextVideo) {
            onNextVideoClick()
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
            isPlayingClick = isPlayingClick
        )

        PlayerControls(
            modifier = Modifier.fillMaxSize(),
            isVisible = isControlsVisible,
            isPlaying = { isPlaying },
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

        if (playbackState == STATE_BUFFERING) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                strokeWidth = 4.dp,
                backgroundColor = LightGreen700.copy(alpha = 0.4f),
                strokeCap = StrokeCap.Butt,
            )
        }
    }
}

@Composable
private fun VideoPlayer(
    exoPlayer: ExoPlayer,
    isPlayingClick: (Boolean) -> Unit
) {
    val context = LocalContext.current

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


