package com.miraelDev.hikari.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }

    fun navigateToFilterScreen() {
        navHostController.navigate(Screen.Filter.route) {
            launchSingleTop = true
        }
    }

    fun navigateToSettingsScreen() {
        navHostController.navigate(Screen.Settings.route) {
            launchSingleTop = true
        }
    }

    fun navigateToSettingsItem(route: String) {
        navHostController.navigate(route) { launchSingleTop = true }
    }

    fun navigateToAnimeDetail(animeId: Int) {
        navHostController.navigate(Screen.AnimeDetail.getRouteWithArgs(animeId)) {
        }
    }

    fun navigateToVideoView(animeId: Int, videoId: Int) {
        navHostController.navigate(Screen.VideoView.getRouteWithArgs(animeId, videoId)) {
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}
