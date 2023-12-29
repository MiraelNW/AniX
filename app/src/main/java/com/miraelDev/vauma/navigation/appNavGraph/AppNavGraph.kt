package com.miraelDev.vauma.navigation.appNavGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.miraelDev.vauma.navigation.Screen

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
    homeCategoriesScreenContent: @Composable (Int) -> Unit,
    accountScreen: @Composable () -> Unit,
) {
    NavHost(
        navController = navHosController,
        startDestination = Screen.HomeAndCategories.route
    ) {


        homeAndCategoriesNavGraph(
            homeScreenContent = homeScreenContent,
            homeCategoriesScreenContent = homeCategoriesScreenContent
        )

        searchAndFilterNavGraph(
            searchScreenContent = searchScreenContent,
            filterScreenContent = filterScreenContent
        )
        composable(
            Screen.Favourite.route,
        ) {
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