package com.miraelDev.vauma.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.util.UnstableApi
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.miraeldev.navigation.decompose.authComponent.AuthRootComponent
import com.miraeldev.navigation.decompose.authComponent.DefaultAuthRootComponent
import com.miraeldev.models.auth.AuthState
import com.miraeldev.signin.presentation.SignInScreen
import com.miraeldev.signup.presentation.signUpScreen.SignUpScreen
import com.miraeldev.theme.VaumaTheme
import com.miraeldev.theme.LocalOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@UnstableApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val viewModel: MainViewModel by viewModels()

        var readyToDrawStartScreen = false

        splashScreen.setKeepOnScreenCondition { !readyToDrawStartScreen }

        val component = DefaultAuthRootComponent(defaultComponentContext())

        setContent {

            val systemUiController = rememberSystemUiController()

            val isSystemDark = isSystemInDarkTheme()

            val darkModeUserChoice by viewModel.isDarkThemeFlow.collectAsState()

            var darkTheme = rememberSaveable { mutableStateOf(false) }

            darkTheme.value = isSystemDark || darkModeUserChoice

            var shouldShowSystemBars by rememberSaveable { mutableStateOf(true) }


            val authState by viewModel.authState.collectAsStateWithLifecycle()

            DisposableEffect(Unit) {
                enableEdgeToEdge()
                onDispose {}
            }

            VaumaTheme(darkTheme = darkTheme) {

                var useDarkIcons by rememberSaveable { mutableStateOf(false) }
                useDarkIcons = darkTheme.value
                DisposableEffect(systemUiController, useDarkIcons) {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !useDarkIcons
                    )
                    onDispose {}
                }

                when (authState) {
                    is AuthState.Authorized -> {
//                        MainScreen(
//                            onThemeButtonClick = {
//                                darkTheme.value = !darkTheme.value
//                                viewModel.setThemeMode(darkTheme.value)
//                                useDarkIcons = !useDarkIcons
//                            },
//                            onReadyToDrawStartScreen = {
//                                readyToDrawStartScreen = true
//                            },
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
                    }

                    is AuthState.NotAuthorized -> {
                            AuthRootContent(component, onReadyToDrawStartScreen = { readyToDrawStartScreen = true })
//                        AuthScreen(onReadyToDrawStartScreen = { readyToDrawStartScreen = true })
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

@Composable
private fun AuthRootContent(component: DefaultAuthRootComponent, onReadyToDrawStartScreen: () -> Unit) {

    LaunchedEffect(key1 = Unit) {
        onReadyToDrawStartScreen()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Children(
            stack = component.stack
        ) {
            when (val instance = it.instance) {
                is AuthRootComponent.Child.SignIn -> {
                    SignInScreen(component = instance.component)
                }
                is AuthRootComponent.Child.SignUp -> {
                    SignUpScreen(component = instance.component)
                }
            }
        }
    }
}
