package com.miraelDev.vauma.navigation.appNavGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen

fun NavGraphBuilder.searchAndFilterNavGraph(
    searchScreenContent: @Composable () -> Unit,
    filterScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.Search.route,
        route = Screen.SearchAndFilter.route
    ) {
        composable(
            route = Screen.Search.route,
            enterTransition = null,
//            {
//                slideIntoContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Right,
//                    animationSpec = tween(700)
//                )
//            },
            exitTransition = null,
//            {
//                slideOutOfContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Left,
//                    animationSpec = tween(700)
//                )
//            }
        ) {
            searchScreenContent()
        }
        composable(route = Screen.Filter.route) {
            filterScreenContent()
        }
    }
}