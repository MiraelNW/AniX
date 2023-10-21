package com.miraelDev.vauma.presentation.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.miraelDev.vauma.navigation.authNavGraph.AuthNavGraph
import com.miraelDev.vauma.navigation.rememberAuthNavigationState
import com.miraelDev.vauma.presentation.auth.codeVerifyResetPassword.CodeVerifyResetPassword
import com.miraelDev.vauma.presentation.auth.codeVerifyScreen.CodeVerifyScreen
import com.miraelDev.vauma.presentation.auth.codeVerifyScreen.EmailChooseScreen
import com.miraelDev.vauma.presentation.auth.resetPassword.ResetPassword
import com.miraelDev.vauma.presentation.auth.signInScreen.SignInScreen
import com.miraelDev.vauma.presentation.auth.signUpScreen.SignUpScreen

@Composable
fun AuthScreen(onReadyToDrawStartScreen: () -> Unit) {

    val navigationState = rememberAuthNavigationState()

    LaunchedEffect(key1 = Unit) {
        onReadyToDrawStartScreen()
    }

    AuthNavGraph(
        navHosController = navigationState.navHostController,
        signInScreenContent = {
            SignInScreen(
                onForgetPasswordClick = {
                    navigationState.navigateToEmailChooseScreen()
                },
                navigateToSignUpScreen = {
                    navigationState.navigateToSignUpScreen()
                }
            )
        },
        signUpScreenContent = {
            SignUpScreen(
                signUp = { email ->
                    navigationState.navigateToCodeVerifyScreen(email)
                },
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
        codeVerifyScreenContent = { email ->
            CodeVerifyScreen(
                email = email,
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
        emailChooseScreenContent = {
            EmailChooseScreen(
                navigateToCodeVerify = { email ->
                    navigationState.navigateToCodeVerifyResetPasswordScreen(email)
                },
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
        codeVerifyResetPasswordScreenContent = { email ->
            CodeVerifyResetPassword(
                email = email,
                navigateToResetPasswordScreen = {
                    navigationState.navigateToResetPassword()
                },
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
        resetPasswordScreenContent = {
            ResetPassword(
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
    )
}