package com.miraelDev.hikari.presentation.MainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.guru.composecookbook.theme.HikariTheme
import com.miraelDev.hikari.ui.theme.AppThemeState
import com.miraelDev.hikari.ui.theme.ColorPallet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@UnstableApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val viewModel: MainViewModel by viewModels()
        installSplashScreen().setKeepOnScreenCondition { viewModel.isLoading.value }
        setContent {
            val systemUiController = rememberSystemUiController()

            val isSystemDark = isSystemInDarkTheme()

            var darkTheme by rememberSaveable {
                mutableStateOf(isSystemDark)
            }

            var landscape by rememberSaveable { mutableStateOf(0) }

            var shouldShowSystemBars by rememberSaveable { mutableStateOf(true) }

            HikariTheme(darkTheme) {
                var useDarkIcons by rememberSaveable { mutableStateOf(darkTheme) }

                DisposableEffect(systemUiController, useDarkIcons) {
                    systemUiController.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = !useDarkIcons
                    )
                    onDispose {}
                }

                MainScreen(
                        onThemeButtonClick = {
                            darkTheme = !darkTheme
                            useDarkIcons = !useDarkIcons
                        },
                        onFullScreenToggle = { landscape = it },
                        onVideoViewClick = { isVideoViewOpen ->
                            if (!darkTheme) {
                                useDarkIcons = !useDarkIcons
                            }
                            when (isVideoViewOpen) {
                                BACK -> shouldShowSystemBars = true
                                ON_VIDEO_VIEW -> shouldShowSystemBars = false
                            }
                        },
                        onColorThemeChoose = {}
                )
            }

            observeState(isFullScreen = landscape, shouldShowSystemBars = shouldShowSystemBars)
        }
    }

    private fun observeState(isFullScreen: Int, shouldShowSystemBars: Boolean) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (isFullScreen == 1 && !shouldShowSystemBars) {
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

