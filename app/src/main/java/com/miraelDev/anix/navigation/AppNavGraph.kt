package com.miraelDev.anix.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.miraelDev.anix.domain.models.AnimeInfo

@Composable
fun AppNavGraph(
    navHosController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    libraryScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    filterScreenContent: @Composable () -> Unit,
    notificationScreenContent: @Composable () -> Unit,
    languageScreenContent: @Composable () -> Unit,
    privacyPolicyScreenContent: @Composable () -> Unit,
    colorPaletteScreenContent: @Composable () -> Unit,
    animeDetailScreenContent: @Composable (Int) -> Unit,
    videoViewScreenContent: @Composable (Int) -> Unit,
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
        composable(Screen.Library.route) {
            libraryScreenContent()
        }
        animeDetailAndVideoView(
            animeDetailScreenContent = animeDetailScreenContent,
            videoViewScreenContent = videoViewScreenContent
        )
    }
}