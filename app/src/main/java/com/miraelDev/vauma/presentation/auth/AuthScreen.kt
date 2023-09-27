package com.miraelDev.vauma.presentation.auth

import androidx.compose.runtime.Composable
import com.miraelDev.vauma.navigation.AuthNavGraph
import com.miraelDev.vauma.navigation.rememberNavigationState
import com.miraelDev.vauma.presentation.auth.signInScreen.SignInScreen
import com.miraelDev.vauma.presentation.auth.signUpScreen.SignUpScreen

@Composable
fun AuthScreen(
    signUp: () -> Unit,
    signIn:() -> Unit
) {

    val navigationState = rememberNavigationState()

    AuthNavGraph(
        navHosController = navigationState.navHostController,
        signInScreenContent = {
            SignInScreen(
                signIn = signIn,
                navigateToSignUpScreen = {
                    navigationState.navigateToSignUpScreen()
                }
            )
        },
        signUpScreenContent = {
            SignUpScreen(
                signUp = signUp,
                onBackClicked = {
                    navigationState.navHostController.popBackStack()
                }
            )
        }
    )
}