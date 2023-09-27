package com.miraelDev.vauma.presentation.mainScreen

import VideoViewScreen
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.miraelDev.vauma.navigation.AppNavGraph
import com.miraelDev.vauma.navigation.Screen
import com.miraelDev.vauma.navigation.rememberNavigationState
import com.miraelDev.vauma.presentation.accountScreen.AccountScreen
import com.miraelDev.vauma.presentation.accountScreen.settings.downloadSettingsScreen.DownloadSettings
import com.miraelDev.vauma.presentation.accountScreen.settings.editProfileScreen.EditProfileScreen
import com.miraelDev.vauma.presentation.animeInfoDetailAndPlay.AnimeDetailScreen
import com.miraelDev.vauma.presentation.animeListScreen.animeList.HomeScreen
import com.miraelDev.vauma.presentation.accountScreen.settings.notificationsScreen.NotificationScreen
import com.miraelDev.vauma.presentation.accountScreen.settings.privacyPolicy.PrivacyPolicyScreen
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
                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
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
                },

                accountScreen = {
                    AccountScreen(
                        onDarkThemeClick = onThemeButtonClick,
                        onSettingItemClick = { index ->
                            val settingScreen = when (index) {
                                0 -> Screen.EditProfile.route
                                1 -> Screen.Notifications.route
                                2 -> Screen.DownloadVideo.route
                                3 -> Screen.Language.route
                                4 -> Screen.PrivacyPolicy.route
                                else -> Screen.Notifications.route
                            }
                            navigationState.navigateToSettingsItem(settingScreen)
                        }
                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                },

                downloadVideoScreenContent = {
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
                    DownloadSettings(
                        onBackPressed = {
                            navigationState.navHostController.popBackStack()
                        }
                    )
                },

                editProfileScreenContent = {
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
                    EditProfileScreen(
                        onBackPressed = {
                            navigationState.navHostController.popBackStack()
                        }
                    )
                },

                notificationScreenContent = {
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
                    NotificationScreen(
                        onBackPressed = {
                            navigationState.navHostController.popBackStack()
                        }
                    )
                },

                privacyPolicyScreenContent = {
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
                    PrivacyPolicyScreen(
                        onBackPressed = {
                            navigationState.navHostController.popBackStack()
                        }
                    )
                },

                )
        }
    }

}



