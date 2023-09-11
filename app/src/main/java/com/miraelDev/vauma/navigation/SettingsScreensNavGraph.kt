package com.miraelDev.vauma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.settingsScreens(
    settingsScreenContent: @Composable () -> Unit,
    notificationScreenContent: @Composable () -> Unit,
    languageScreenContent: @Composable () -> Unit,
    privacyPolicyScreenContent: @Composable () -> Unit,
    colorPaletteScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.Settings.route,
        route = Screen.DifferentSettings.route
    ) {
        composable(route = Screen.Settings.route) {
            settingsScreenContent()
        }
        composable(route = Screen.Notifications.route) {
            notificationScreenContent()
        }
        composable(route = Screen.Language.route) {
            languageScreenContent()
        }
        composable(route = Screen.PrivacyPolicy.route) {
            privacyPolicyScreenContent()
        }
        composable(route = Screen.ColorPalette.route) {
            colorPaletteScreenContent()
        }
    }
}