package com.miraelDev.vauma.navigation.authNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen

fun NavGraphBuilder.signUpAndCodeVerifyNavGraph(
    signUpScreenContent: @Composable () -> Unit,
    codeVerifyScreenContent: @Composable (String) -> Unit
) {
    navigation(
        startDestination = Screen.SignUp.route,
        route = Screen.SignUpAndCodeVerify.route
    ) {
        composable(route = Screen.SignUp.route) {
            signUpScreenContent()
        }
        composable(
            route = Screen.EmailCodeVerify.route,
            arguments = listOf(
                navArgument(name = Screen.KEY_EMAIL) {
                    type = NavType.StringType
                },
            )
        ) {
            val email = it.arguments?.getString(Screen.KEY_EMAIL) ?: ""

            codeVerifyScreenContent(email)
        }
    }
}