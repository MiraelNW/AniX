package com.miraelDev.vauma.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.util.UnstableApi
import com.arkivanov.decompose.defaultComponentContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.miraelDev.vauma.presentation.appRootComponent.DefaultAppRootComponent
import com.miraeldev.theme.LocalOrientation
import com.miraeldev.theme.VaumaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@UnstableApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appRootComponentFactory: DefaultAppRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        var readyToDrawStartScreen = false

        splashScreen.setKeepOnScreenCondition { !readyToDrawStartScreen }

        val componentContext = defaultComponentContext()

        setContent {

            var shouldShowSystemBars by rememberSaveable { mutableStateOf(true) }

            DisposableEffect(Unit) {
                enableEdgeToEdge()
                onDispose {}
            }

            AppRootContent(
                component = appRootComponentFactory.create(componentContext = componentContext),
                onReadyToDrawStartScreen = { readyToDrawStartScreen = true }
            )

//                        MainScreen(
//                            onVideoViewClick = { isVideoViewOpen ->
//                                if (!darkTheme.value) {
//                                    useDarkIcons = !useDarkIcons
//                                }
//                                when (isVideoViewOpen) {
//                                    BACK -> shouldShowSystemBars = true
//                                    ON_VIDEO_VIEW -> shouldShowSystemBars = false
//                                }
//                            }
//                        )
//                    }


            observeState(
                isFullScreen = LocalOrientation.current,
                shouldShowSystemBars = shouldShowSystemBars
            )
        }
    }

    private fun observeState(isFullScreen: Int, shouldShowSystemBars: Boolean) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (isFullScreen == Configuration.ORIENTATION_LANDSCAPE && !shouldShowSystemBars) {
                    hideSystemBars()
                } else {
                    showSystemBars()
                }

            }
        }
    }

    private fun hideSystemBars() {
        // Configure the behavior of the hidden system bars
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun showSystemBars() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }

    companion object {
        private const val BACK = 0
        private const val ON_VIDEO_VIEW = 1
    }
}
