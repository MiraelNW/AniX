package com.miraelDev.vauma.navigation.appNavGraph

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen
import com.miraelDev.vauma.navigation.animations.scaleIntoContainer
import com.miraelDev.vauma.navigation.animations.scaleOutOfContainer

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
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://vauma.com/anime-detail/{anime_detail_id}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument(name = Screen.KEY_ANIME_DETAIL_ID) {
                    type = NavType.IntType
                    defaultValue = 1
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