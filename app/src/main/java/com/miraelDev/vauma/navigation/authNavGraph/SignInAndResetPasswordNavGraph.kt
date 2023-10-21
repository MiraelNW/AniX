package com.miraelDev.vauma.navigation.authNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.miraelDev.vauma.navigation.Screen

fun NavGraphBuilder.signInAndResetPasswordNavGraph(
    signInScreenContent: @Composable () -> Unit,
    resetPasswordScreenContent: @Composable () -> Unit,
    emailChooseScreenContent: @Composable () -> Unit,
    codeVerifyResetPasswordScreenContent: @Composable (String) -> Unit,
) {
    navigation(
        startDestination = Screen.SignIn.route,
        route = Screen.SignInAndResetPassword.route
    ) {
        composable(route = Screen.SignIn.route) {
            signInScreenContent()
        }
        resetPasswordNavGraph(
            resetPasswordScreenContent = resetPasswordScreenContent,
            emailChooseScreenContent = emailChooseScreenContent,
            codeVerifyResetPasswordScreenContent = codeVerifyResetPasswordScreenContent
        )
    }
}