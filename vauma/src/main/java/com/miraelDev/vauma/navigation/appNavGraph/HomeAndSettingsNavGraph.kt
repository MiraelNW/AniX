package com.miraelDev.vauma.navigation.appNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen

fun NavGraphBuilder.homeAndSettingsNavGraph(
    homeScreenContent: @Composable () -> Unit,
    settingsScreenContent: @Composable () -> Unit,
    notificationScreenContent: @Composable () -> Unit,
    languageScreenContent: @Composable () -> Unit,
    privacyPolicyScreenContent: @Composable () -> Unit,
    colorPaletteScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.Home.route,
        route = Screen.HomeAndSettings.route
    ) {
//        settingsScreens(
//            settingsScreenContent = settingsScreenContent,
//            notificationScreenContent = notificationScreenContent,
//            languageScreenContent = languageScreenContent,
//            privacyPolicyScreenContent = privacyPolicyScreenContent,
//            colorPaletteScreenContent = colorPaletteScreenContent,
//        )
        composable(route = Screen.Home.route) {
            homeScreenContent()
        }
    }
}