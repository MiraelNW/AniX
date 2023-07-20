package com.miraelDev.anix.presentation.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.util.UnstableApi

import androidx.media3.exoplayer.ExoPlayer

import com.miraelDev.anix.domain.models.PlayerWrapper
import com.miraelDev.anix.navigation.AppNavGraph
import com.miraelDev.anix.navigation.Screen
import com.miraelDev.anix.navigation.rememberNavigationState
import com.miraelDev.anix.presentation.AnimeInfoDetailAndPlay.AnimeDetailScreen
import com.miraelDev.anix.presentation.AnimeListScreen.HomeScreen
import com.miraelDev.anix.presentation.AnimeListScreen.SettingsScreen.ColorPalette.ColorPaletteScreen
import com.miraelDev.anix.presentation.AnimeListScreen.SettingsScreen.LanguageScreen.LanguageScreen
import com.miraelDev.anix.presentation.AnimeListScreen.SettingsScreen.NotificationsScreen.NotificationScreen
import com.miraelDev.anix.presentation.AnimeListScreen.SettingsScreen.PrivacyPolicy.PrivacyPolicyScreen
import com.miraelDev.anix.presentation.AnimeListScreen.SettingsScreen.SettingsScreen
import com.miraelDev.anix.presentation.FavouriteListScreen.FavouriteListScreen
import com.miraelDev.anix.presentation.MainScreen.navigation.BottomBar
import com.miraelDev.anix.presentation.SearchAimeScreen.FilterScreen
import com.miraelDev.anix.presentation.SearchAnimeScreen
import com.miraelDev.anix.presentation.VideoView.VideoView

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@UnstableApi
fun  MainScreen(
    onThemeButtonClick: () -> Unit,
    onFullScreenToggle: (Boolean) -> Unit,
    landscape : Boolean
) {

    var shouldShowBottomBar by remember {
        mutableStateOf(true)
    }

    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomBar(navigationState = navigationState)
            }
        }

    ) {
        AppNavGraph(
            navHosController = navigationState.navHostController,

            homeScreenContent = {
                shouldShowBottomBar = true
                HomeScreen(
                    onThemeButtonClick = onThemeButtonClick,
                    onSettingsClick = {
                        navigationState.navigateToSettingsScreen()
                    },
                    onAnimeItemClick = { animeId ->
                        navigationState.navigateToAnimeDetail(animeId)
                    }
                )
            },

            settingsScreenContent = {
                shouldShowBottomBar = false
                SettingsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onSettingItemClick = { index ->
                        val settingScreen = when (index) {
                            0 -> Screen.Notifications.route
                            1 -> Screen.Language.route
                            2 -> Screen.PrivacyPolicy.route
                            3 -> Screen.ColorPalette.route
                            else -> Screen.Notifications.route
                        }
                        navigationState.navigateToSettingsItem(settingScreen)
                    },
                )
            },

            languageScreenContent = {
                shouldShowBottomBar = false
                LanguageScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack(
                            route = Screen.Settings.route,
                            inclusive = false
                        )
                    }
                )
            },

            notificationScreenContent = {
                shouldShowBottomBar = false
                NotificationScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack(
                            route = Screen.Settings.route,
                            inclusive = false
                        )
                    }
                )
            },

            colorPaletteScreenContent = {
                shouldShowBottomBar = false
                ColorPaletteScreen(
//                    onBackPressed = {
//                        navigationState.navHostController.popBackStack(
//                            route = Screen.Settings.route,
//                            inclusive = false
//                        )
//                    }
                )
            },

            privacyPolicyScreenContent = {
                shouldShowBottomBar = false
                PrivacyPolicyScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack(
                            route = Screen.Settings.route,
                            inclusive = false
                        )
                    }
                )
            },

            libraryScreenContent = {
                shouldShowBottomBar = true
                FavouriteListScreen(
                    onAnimeItemClick = { animeId ->
                        navigationState.navigateToAnimeDetail(animeId)
                    }
                )
            },

            filterScreenContent = {
                shouldShowBottomBar = false
                FilterScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            },

            searchScreenContent = {
                shouldShowBottomBar = true
                SearchAnimeScreen(
                    onFilterClicked = {
                        navigationState.navigateToFilterScreen()
                    },
                    onAnimeItemClick = { animeId ->
                        navigationState.navigateToAnimeDetail(animeId)
                    }
                )
            },
            animeDetailScreenContent = { animeId ->
                shouldShowBottomBar = false
                AnimeDetailScreen(
                    animeId = animeId,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onAnimeItemClick = { animeIdNavArg ->
                        navigationState.navigateToAnimeDetail(animeIdNavArg)
                    },
                    onSeriesClick = { animeIdNavArg ->
                        navigationState.navigateToVideoView(animeIdNavArg)
                    }
                )
            },
            videoViewScreenContent = { animeId ->
                shouldShowBottomBar = false
                VideoView(
                    modifier = Modifier.fillMaxSize(),
                    onFullScreenToggle = onFullScreenToggle,
                    landscape = landscape,
                    navigateBack = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            }
        )
    }
}



