package com.miraelDev.vauma.navigation.appNavGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen
import com.miraelDev.vauma.navigation.animations.ScaleTransitionDirection
import com.miraelDev.vauma.navigation.animations.scaleIntoContainer
import com.miraelDev.vauma.navigation.animations.scaleOutOfContainer

fun NavGraphBuilder.homeAndCategoriesNavGraph(
    homeScreenContent: @Composable () -> Unit,
    homeCategoriesScreenContent: @Composable (Int) -> Unit
) {
    navigation(
        startDestination = Screen.Home.route,
        route = Screen.HomeAndCategories.route
    ) {
        composable(
            route = Screen.Home.route,
        ) {
            homeScreenContent()
        }
        composable(
            route = Screen.Categories.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_CATEGORIES_ID) {
                    type = NavType.IntType
                },
            )
        ) {
            val categoryId = it.arguments?.getInt(Screen.KEY_CATEGORIES_ID) ?: 0

            homeCategoriesScreenContent(categoryId)
        }
    }
}