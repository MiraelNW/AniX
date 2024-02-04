package com.miraelDev.vauma.navigation.authNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.miraelDev.vauma.navigation.Screen

@Composable
fun AuthNavGraph(
    navHosController: NavHostController,
    signInScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    resetPasswordScreenContent: @Composable (String) -> Unit,
    emailChooseScreenContent: @Composable () -> Unit,
    codeVerifyScreenContent: @Composable (String,String) -> Unit,
    codeVerifyResetPasswordScreenContent: @Composable (String) -> Unit,
) {
    NavHost(
        navController = navHosController,
        startDestination = Screen.SignInAndResetPassword.route
    ) {

        signInAndResetPasswordNavGraph(
            signInScreenContent = signInScreenContent,
            resetPasswordScreenContent = resetPasswordScreenContent,
            emailChooseScreenContent = emailChooseScreenContent,
            codeVerifyResetPasswordScreenContent = codeVerifyResetPasswordScreenContent
        )

        signUpAndCodeVerifyNavGraph(
            signUpScreenContent = signUpScreenContent,
            codeVerifyScreenContent = codeVerifyScreenContent
        )
    }
}