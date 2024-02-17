package com.miraelDev.vauma.navigation.appNavGraph

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.media3.common.util.UnstableApi
import com.miraelDev.vauma.navigation.animations.rememberNavigationState
import com.miraeldev.account.presentation.settings.downloadSettingsScreen.DownloadSettings
import com.miraeldev.account.presentation.settings.notificationsScreen.NotificationScreen
import com.miraeldev.account.presentation.settings.privacyPolicy.PrivacyPolicyScreen
import com.miraeldev.search.presentation.filterScreen.FilterScreen
import com.miraeldev.theme.LocalOrientation
import com.miraeldev.video.presentation.VideoViewScreen

private const val BACK = 0
private const val ON_VIDEO_VIEW = 1

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@UnstableApi
fun MainScreen(
    onThemeButtonClick: () -> Unit,
    onVideoViewClick: (Int) -> Unit,
    onReadyToDrawStartScreen: () -> Unit
) {
    val orientation = LocalOrientation.current

    var shouldShowBottomBar by remember { mutableStateOf(orientation == Configuration.ORIENTATION_LANDSCAPE) }
    var shouldShowNavRail by remember { mutableStateOf(orientation == Configuration.ORIENTATION_LANDSCAPE) }

    val navigationState = rememberNavigationState()

    LaunchedEffect(key1 = Unit) {
        onReadyToDrawStartScreen()
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
//                BottomBar()
            }
        }
    ) {
        Row(
            Modifier
                .fillMaxSize()
        ) {
            if (shouldShowNavRail) {
//                NavigationRailContent(navigationState = navigationState)
            }

            AppNavGraph(
                navHosController = navigationState.navHostController,

                homeScreenContent = {
//                    HomeScreen(
//                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
//                        onSeeAllClick = navigationState::navigateToCategories,
//                        onPlayClick = { id ->
//                            onVideoViewClick(ON_VIDEO_VIEW)
//                            navigationState.navigateToVideoViewThroughDetailScreen(id)
//                        }
//                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                },

                homeCategoriesScreenContent = { id ->
//                    AnimeCategoriesScreen(
//                        categoryId = id,
//                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
//                    )
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
                },

                favouriteScreenContent = {
//                    FavouriteListScreen(
//                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
//                        navigateToSearchScreen = {
//                            navigationState.navigateTo(Screen.SearchAndFilter.route)
//                        }
//                    )
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
//                    SearchAnimeScreen(
//                        onFilterClicked = navigationState::navigateToFilterScreen,
//                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
//                    )
                    shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                    shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                },

                animeDetailScreenContent = { animeId ->
                    shouldShowBottomBar = false
                    shouldShowNavRail = false
//                    AnimeDetailScreen(
//                        animeId = animeId,
//                        onBackPressed = {
//                            navigationState.navHostController.popBackStack()
//                        },
//                        onAnimeItemClick = navigationState::navigateToAnimeDetail,
//                        onSeriesClick = {
//                            onVideoViewClick(ON_VIDEO_VIEW)
//                            navigationState.navigateToVideoView()
//                        }
//                    )
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
//                    AccountScreen(
//                        onDarkThemeClick = onThemeButtonClick,
//                        onSettingItemClick = { index ->
//                            val settingScreen = when (index) {
//                                0 -> Screen.EditProfile.route
//                                1 -> Screen.Notifications.route
//                                2 -> Screen.DownloadVideo.route
//                                3 -> Screen.Language.route
//                                4 -> Screen.PrivacyPolicy.route
//                                else -> Screen.Notifications.route
//                            }
//                            navigationState.navigateToSettingsItem(settingScreen)
//                        }
//                    )
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
//                    EditProfileScreen(
//                        onBackPressed = {
//                            navigationState.navHostController.popBackStack()
//                        }
//                    )
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



