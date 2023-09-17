package com.miraelDev.vauma.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHosController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    filterScreenContent: @Composable () -> Unit,
    notificationScreenContent: @Composable () -> Unit,
    languageScreenContent: @Composable () -> Unit,
    privacyPolicyScreenContent: @Composable () -> Unit,
    colorPaletteScreenContent: @Composable () -> Unit,
    animeDetailScreenContent: @Composable (Int) -> Unit,
    videoViewScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHosController,
        startDestination = Screen.HomeAndSettings.route
    ) {

        homeAndSettingsNavGraph(
            homeScreenContent = homeScreenContent,
            settingsScreenContent = settingsScreenContent,
            notificationScreenContent = notificationScreenContent,
            languageScreenContent = languageScreenContent,
            privacyPolicyScreenContent = privacyPolicyScreenContent,
            colorPaletteScreenContent = colorPaletteScreenContent,
        )
        searchAndFilterNavGraph(
            searchScreenContent = searchScreenContent,
            filterScreenContent = filterScreenContent
        )
        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        animeDetailAndVideoView(
            animeDetailScreenContent = animeDetailScreenContent,
            videoViewScreenContent = videoViewScreenContent
        )
    }
}