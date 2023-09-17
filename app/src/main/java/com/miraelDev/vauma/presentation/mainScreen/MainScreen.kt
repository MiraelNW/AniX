package com.miraelDev.vauma.presentation.mainScreen

import VideoViewScreen
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.miraelDev.vauma.presentation.mainScreen.navigation.NavigationRailContent
import com.miraelDev.vauma.presentation.searchAimeScreen.FilterScreen
import com.miraelDev.vauma.presentation.searchAimeScreen.SearchAnimeScreen

private const val BACK = 0
private const val ON_VIDEO_VIEW = 1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@UnstableApi
fun MainScreen(
    onThemeButtonClick: () -> Unit,
    onVideoViewClick: (Int) -> Unit,
) {

    var shouldShowBottomBar by rememberSaveable { mutableStateOf(true) }
    var shouldShowNavRail by rememberSaveable { mutableStateOf(false) }

    val orientation = LocalOrientation.current
    LaunchedEffect(key1 = orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            shouldShowBottomBar = false
            shouldShowNavRail = true
        } else {
            shouldShowBottomBar = true
            shouldShowNavRail = false
        }
    }

    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomBar(navigationState = navigationState)
            }
        }
    ) {
        Row(
            Modifier
                .fillMaxSize()
        ) {
            if (shouldShowNavRail) {
                NavigationRailContent(navigationState = navigationState)
            }

            AppNavGraph(
                navHosController = navigationState.navHostController,
                homeScreenContent = {
                    HomeScreen(
                        onThemeButtonClick = onThemeButtonClick,
                        onSettingsClick = navigationState::navigateToSettingsScreen,
                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                },

                settingsScreenContent = {
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
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
                    shouldShowNavRail = false
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
                    shouldShowNavRail = false
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
                    shouldShowNavRail = false
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
                   FavouriteListScreen(
                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
                        navigateToSearchScreen = navigationState::navigateTo
                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                },

                filterScreenContent = {
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
                    FilterScreen(
                        onBackPressed = {
                            navigationState.navHostController.popBackStack()
                           }
                    )
                },

                searchScreenContent = {
                    SearchAnimeScreen(
                        onFilterClicked = navigationState::navigateToFilterScreen,
                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                },

                animeDetailScreenContent = { animeId ->
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
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
                    shouldShowNavRail = false
                    VideoViewScreen(
                        navigateBack = {
                            onVideoViewClick(BACK)
                            navigationState.navHostController.popBackStack()
                        }
                    )
                }

            )
        }
    }

}



