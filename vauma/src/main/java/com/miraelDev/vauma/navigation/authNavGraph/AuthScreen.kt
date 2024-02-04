package com.miraelDev.vauma.navigation.authNavGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.miraelDev.vauma.navigation.rememberAuthNavigationState
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.EmailChooseScreen
import com.miraeldev.forgotpassword.presentation.codeVerifyResetPassword.CodeVerifyResetPassword
import com.miraeldev.signup.presentation.codeVerifyScreen.CodeVerifyScreen
import com.miraeldev.forgotpassword.presentation.resetPassword.ResetPasswordScreen
import com.miraeldev.signin.presentation.SignInScreen
import com.miraeldev.signup.presentation.signUpScreen.SignUpScreen

@Composable
fun AuthScreen(onReadyToDrawStartScreen: () -> Unit) {

    val navigationState = rememberAuthNavigationState()

    LaunchedEffect(key1 = Unit) {
        onReadyToDrawStartScreen()
    }

    AuthNavGraph(
        navHosController = navigationState.navHostController,
        signInScreenContent = {
//            SignInScreen(
//                onForgetPasswordClick = {
//                    navigationState.navigateToEmailChooseScreen()
//                },
//                navigateToSignUpScreen = {
//                    navigationState.navigateToSignUpScreen()
//                }
//            )
        },
        signUpScreenContent = {
//            SignUpScreen(
//                navigateToOtpScreen = { email, password ->
//                    navigationState.navigateToCodeVerifyScreen(email, password)
//                },
//                onBackPressed = {
//                    navigationState.navHostController.popBackStack()
//                }
//            )
        },
        codeVerifyScreenContent = { email, password ->
            CodeVerifyScreen(
                email = email,
                password = password,
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
                    navigationState.navigateToResetPassword(email)
                },
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
        resetPasswordScreenContent = { email ->
            ResetPasswordScreen(
                email = email,
                onBackPressed = {
                    navigationState.navHostController.popBackStack()
                }
            )
        },
    )
}