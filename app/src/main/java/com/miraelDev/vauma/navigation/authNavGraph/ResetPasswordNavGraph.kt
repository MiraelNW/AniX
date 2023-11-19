package com.miraelDev.vauma.navigation.authNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen

fun NavGraphBuilder.resetPasswordNavGraph(
    resetPasswordScreenContent: @Composable (String) -> Unit,
    emailChooseScreenContent: @Composable () -> Unit,
    codeVerifyResetPasswordScreenContent: @Composable (String) -> Unit
) {
    navigation(
        startDestination = Screen.EmailChoose.route,
        route = Screen.ResetPasswordGraph.route
    ) {

        composable(route = Screen.EmailChoose.route) {
            emailChooseScreenContent()
        }

        composable(
            route = Screen.EmailCodeVerifyResetPassword.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_EMAIL) {
                    type = NavType.StringType
                },
            )
        ) {
            val email = it.arguments?.getString(Screen.KEY_EMAIL) ?: ""

            codeVerifyResetPasswordScreenContent(email)
        }

        composable(
            route = Screen.ResetPassword.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_EMAIL) {
                    type = NavType.StringType
                },
            )
        ) {
            val email = it.arguments?.getString(Screen.KEY_EMAIL) ?: ""

            resetPasswordScreenContent(email)
        }
    }
}