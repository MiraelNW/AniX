package com.miraelDev.hikari.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.searchAndFilterNavGraph(
    searchScreenContent: @Composable () -> Unit,
    filterScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.Search.route,
        route = Screen.SearchAndFilter.route
    ) {
        composable(route = Screen.Search.route) {
            searchScreenContent()
        }
        composable(route = Screen.Filter.route) {
            filterScreenContent()
        }
    }
}