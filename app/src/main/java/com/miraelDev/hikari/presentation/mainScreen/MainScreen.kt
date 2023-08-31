package com.miraelDev.hikari.presentation.mainScreen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.miraelDev.hikari.navigation.AppNavGraph
import com.miraelDev.hikari.navigation.Screen
import com.miraelDev.hikari.navigation.rememberNavigationState
import com.miraelDev.hikari.presentation.animeInfoDetailAndPlay.AnimeDetailScreen
import com.miraelDev.hikari.presentation.animeListScreen.animeList.HomeScreen
import com.miraelDev.hikari.presentation.animeListScreen.settingsScreen.ColorPalette.ColorPaletteScreen
import com.miraelDev.hikari.presentation.animeListScreen.settingsScreen.LanguageScreen.LanguageScreen
import com.miraelDev.hikari.presentation.animeListScreen.settingsScreen.NotificationsScreen.NotificationScreen
import com.miraelDev.hikari.presentation.animeListScreen.settingsScreen.PrivacyPolicy.PrivacyPolicyScreen
import com.miraelDev.hikari.presentation.animeListScreen.settingsScreen.SettingsScreen
import com.miraelDev.hikari.presentation.favouriteListScreen.FavouriteListScreen
import com.miraelDev.hikari.presentation.mainScreen.navigation.BottomBar
import com.miraelDev.hikari.presentation.searchAimeScreen.FilterScreen
import com.miraelDev.hikari.presentation.searchAimeScreen.SearchAnimeViewModel
import com.miraelDev.hikari.presentation.searchAimeScreen.SearchAnimeScreen
import com.miraelDev.hikari.presentation.videoView.VideoViewScreen

private const val BACK = 0
private const val ON_VIDEO_VIEW = 1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@UnstableApi
fun MainScreen(
    onThemeButtonClick: () -> Unit,
    onFullScreenToggle: (Int) -> Unit,
    onVideoViewClick: (Int) -> Unit,
    onColorThemeChoose: (Int) -> Unit,
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
                    onSettingsClick = navigationState::navigateToSettingsScreen,
                    onAnimeItemClick = navigationState::navigateToAnimeDetail,
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
                    onBackPressed = {
                        navigationState.navHostController.popBackStack(
                            route = Screen.Settings.route,
                            inclusive = false
                        )
                    },
                    onColorThemeChoose = onColorThemeChoose
                )
            },

            privacyPolicyScreenContent = {
                shouldShowBottomBar = false
                PrivacyPolicyScreen(
                    onBackPressed = {
//                        navigationState.navHostController.popBackStack(
//                            route = Screen.Settings.route,
//                            inclusive = false
//                        )
                    }
                )
            },

            favouriteScreenContent = {
                shouldShowBottomBar = true
                FavouriteListScreen(
                    onAnimeItemClick = navigationState::navigateToAnimeDetail,
                    navigateToSearchScreen = navigationState::navigateToSearchScreen
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

                val viewModel:SearchAnimeViewModel = hiltViewModel()

                SearchAnimeScreen(
                    onFilterClicked = navigationState::navigateToFilterScreen,
                    onAnimeItemClick = navigationState::navigateToAnimeDetail,
                    viewModel = viewModel
                )
            },

            animeDetailScreenContent = { animeId ->
                shouldShowBottomBar = false
                AnimeDetailScreen(
                    animeId = animeId,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onAnimeItemClick = navigationState::navigateToAnimeDetail,
                    onSeriesClick = {
                        onVideoViewClick(ON_VIDEO_VIEW)
                        navigationState.navigateToVideoView()
                    }
                )
            },

            videoViewScreenContent = {
                shouldShowBottomBar = false
                VideoViewScreen(
                    onFullScreenToggle = onFullScreenToggle,
                    navigateBack = {
                        onVideoViewClick(BACK)
                        navigationState.navHostController.popBackStack()
                    }
                )
            }

        )
    }
}



