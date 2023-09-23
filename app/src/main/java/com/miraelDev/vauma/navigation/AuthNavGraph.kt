package com.miraelDev.vauma.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AuthNavGraph(
    navHosController: NavHostController,
    signInScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHosController,
        startDestination = Screen.SignIn.route
    ) {

        composable(Screen.SignIn.route){
            signInScreenContent()
        }

        composable(Screen.SignUp.route){
            signUpScreenContent()
        }
    }
}