package com.miraelDev.vauma.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class AuthNavigationState(
    val navHostController: NavHostController
) {
    fun navigateToSignUpScreen() {
        navHostController.navigate(Screen.SignUp.route) {
            launchSingleTop = true
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }

    fun navigateToCodeVerifyScreen(email: String, password: String) {
        navHostController.navigate(Screen.EmailCodeVerify.getRouteWithArgs(email, password))
    }

    fun navigateToCodeVerifyResetPasswordScreen(email: String) {
        navHostController.navigate(Screen.EmailCodeVerifyResetPassword.getRouteWithArgs(email))
    }

    fun navigateToEmailChooseScreen() {
        navHostController.navigate(Screen.EmailChoose.route) {
            launchSingleTop = true
        }
    }

    fun navigateToResetPassword(email: String) {
        navHostController.navigate((Screen.ResetPassword.getRouteWithArgs(email))) {
            popUpTo(Screen.EmailChoose.route) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}


@Composable
fun rememberAuthNavigationState(
    navHostController: NavHostController = rememberNavController()
): AuthNavigationState {
    return remember {
        AuthNavigationState(navHostController)
    }
}