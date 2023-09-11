package com.miraelDev.vauma.presentation.mainScreen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.media3.common.util.UnstableApi
import com.miraelDev.vauma.navigation.AppNavGraph
import com.miraelDev.vauma.navigation.Screen
import com.miraelDev.vauma.navigation.rememberNavigationState
import com.miraelDev.vauma.presentation.animeInfoDetailAndPlay.AnimeDetailScreen
import com.miraelDev.vauma.presentation.animeListScreen.animeList.HomeScreen
import com.miraelDev.vauma.presentation.animeListScreen.settingsScreen.LanguageScreen.LanguageScreen
import com.miraelDev.vauma.presentation.animeListScreen.settingsScreen.NotificationsScreen.NotificationScreen
import com.miraelDev.vauma.presentation.animeListScreen.settingsScreen.PrivacyPolicy.PrivacyPolicyScreen
import com.miraelDev.vauma.presentation.animeListScreen.settingsScreen.SettingsScreen
import com.miraelDev.vauma.presentation.favouriteListScreen.FavouriteListScreen
import com.miraelDev.vauma.presentation.mainScreen.navigation.BottomBar
import com.miraelDev.vauma.presentation.searchAimeScreen.FilterScreen
import com.miraelDev.vauma.presentation.searchAimeScreen.SearchAnimeScreen
import com.miraelDev.vauma.presentation.videoView.VideoViewScreen

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
//                ColorPaletteScreen(
//                    onBackPressed = {
//                        navigationState.navHostController.popBackStack(
//                            route = Screen.Settings.route,
//                            inclusive = false
//                        )
//                    },
//                    onColorThemeChoose = onColorThemeChoose
//                )
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

                SearchAnimeScreen(
                    onFilterClicked = navigationState::navigateToFilterScreen,
                    onAnimeItemClick = navigationState::navigateToAnimeDetail,
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



