package com.miraelDev.vauma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.animeDetailAndVideoView(
    animeDetailScreenContent: @Composable (Int) -> Unit,
    videoViewScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.AnimeDetail.route,
        route = Screen.AnimeDetailAndVideoView.route
    ) {

        composable(
            route = Screen.AnimeDetail.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_ANIME_DETAIL_ID){
                    type = NavType.IntType
                }

            )
        ) {
            val animeDetailId = it.arguments?.getInt(Screen.KEY_ANIME_DETAIL_ID) ?: 0
            animeDetailScreenContent(animeDetailId)
        }

        composable(
            route = Screen.VideoView.route
        ) {
            videoViewScreenContent()
        }
    }
}