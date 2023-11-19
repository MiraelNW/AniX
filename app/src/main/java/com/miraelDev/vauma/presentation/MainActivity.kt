package com.miraelDev.vauma.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.miraelDev.vauma.navigation.appNavGraph.MainScreen
import com.miraelDev.vauma.navigation.authNavGraph.AuthScreen
import com.miraeldev.auth.AuthState
import com.miraeldev.theme.HikariTheme
import com.miraeldev.theme.LocalOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val viewModel: MainViewModel by viewModels()

        var readyToDrawStartScreen = false

        splashScreen.setKeepOnScreenCondition { false }

        setContent {

            val systemUiController = rememberSystemUiController()

            val isSystemDark = isSystemInDarkTheme()

            val darkModeUserChoice by viewModel.isDarkThemeFlow.collectAsState()

            var darkTheme by rememberSaveable { mutableStateOf(false) }

            darkTheme = isSystemDark || darkModeUserChoice

            var shouldShowSystemBars by rememberSaveable { mutableStateOf(true) }


            val authState by viewModel.authState.collectAsStateWithLifecycle()

            DisposableEffect(Unit) {
                enableEdgeToEdge()
                onDispose {}
            }

            HikariTheme(darkTheme = darkTheme) {

                var useDarkIcons by rememberSaveable { mutableStateOf(false) }
                useDarkIcons = darkTheme
                DisposableEffect(systemUiController, useDarkIcons) {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !useDarkIcons
                    )
                    onDispose {}
                }

                when (authState) {
                    is com.miraeldev.auth.AuthState.Authorized -> {
                        MainScreen(
                            onThemeButtonClick = {
                                darkTheme = !darkTheme
                                viewModel.setThemeMode(darkTheme)
                                useDarkIcons = !useDarkIcons
                            },
                            onReadyToDrawStartScreen = {
                                readyToDrawStartScreen = true
                            },
                            onVideoViewClick = { isVideoViewOpen ->
                                if (!darkTheme) {
                                    useDarkIcons = !useDarkIcons
                                }
                                when (isVideoViewOpen) {
                                    BACK -> shouldShowSystemBars = true
                                    ON_VIDEO_VIEW -> shouldShowSystemBars = false
                                }
                            }
                        )
                    }

                    is AuthState.NotAuthorized -> {
                        AuthScreen(onReadyToDrawStartScreen = { readyToDrawStartScreen = true })
                    }

                    is AuthState.Initial -> {}
                }
            }

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
