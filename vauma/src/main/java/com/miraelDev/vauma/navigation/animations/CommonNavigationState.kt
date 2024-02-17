package com.miraelDev.vauma.navigation.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miraelDev.vauma.navigation.Screen

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

    fun navigateToSettingsItem(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateToCategories(categoryId: Int) {
        navHostController.navigate(Screen.Categories.getRouteWithArgs(categoryId)) {
            launchSingleTop = true
        }
    }

    fun navigateToAnimeDetail(animeId: Int) {
        navHostController.navigate(Screen.AnimeDetail.getRouteWithArgs(animeId))
    }

    fun navigateToVideoView() {
        navHostController.navigate(Screen.VideoView.route) {
            launchSingleTop = true
        }
    }

    fun navigateToVideoViewThroughDetailScreen(id: Int) {
        navHostController.navigate(Screen.AnimeDetail.getRouteWithArgs(id)) {
            launchSingleTop = true
        }
        navHostController.navigate(Screen.VideoView.route) {
            launchSingleTop = true
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
