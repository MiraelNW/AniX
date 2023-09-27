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
    favouriteScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
    filterScreenContent: @Composable () -> Unit,
    notificationScreenContent: @Composable () -> Unit,
    privacyPolicyScreenContent: @Composable () -> Unit,
    animeDetailScreenContent: @Composable (Int) -> Unit,
    videoViewScreenContent: @Composable () -> Unit,
    editProfileScreenContent: @Composable () -> Unit,
    downloadVideoScreenContent: @Composable () -> Unit,
    accountScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHosController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route){
            homeScreenContent()
        }

        searchAndFilterNavGraph(
            searchScreenContent = searchScreenContent,
            filterScreenContent = filterScreenContent
        )
        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        profileNavGraph(
            accountScreen = accountScreen,
            editProfileContent = editProfileScreenContent,
            notificationScreenContent = notificationScreenContent,
            downloadVideoScreenContent = downloadVideoScreenContent,
            privacyPolicyScreenContent = privacyPolicyScreenContent
        )
        animeDetailAndVideoView(
            animeDetailScreenContent = animeDetailScreenContent,
            videoViewScreenContent = videoViewScreenContent
        )
    }
}