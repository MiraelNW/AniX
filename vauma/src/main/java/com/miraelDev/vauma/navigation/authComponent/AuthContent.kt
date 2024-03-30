package com.miraelDev.vauma.navigation.authComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.miraeldev.forgotpassword.presentation.codeVerifyResetPasswordScreen.CodeVerifyRPScreen
import com.miraeldev.forgotpassword.presentation.emailChooseScreen.EmailChooseScreen
import com.miraeldev.forgotpassword.presentation.resetPassword.ResetPasswordScreen
import com.miraeldev.signin.presentation.SignInScreen
import com.miraeldev.signup.presentation.codeVerifyScreen.CodeVerifyScreen
import com.miraeldev.signup.presentation.signUpScreen.SignUpScreen
@Composable
fun AuthContent(component: AuthRootComponent, onReadyToDrawStartScreen: () -> Unit) {

    LaunchedEffect(Unit) {
        onReadyToDrawStartScreen()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Children(
            stack = component.stack
        ) {
            when (val instance = it.instance) {
                is AuthRootComponent.Child.SignIn -> {
                    SignInScreen(component = instance.component)
                }

                is AuthRootComponent.Child.SignUp -> {
                    SignUpScreen(component = instance.component, imageLoader = instance.imageLoader)
                }

                is AuthRootComponent.Child.CodeVerify -> {
                    CodeVerifyScreen(
                        component = instance.component,
                        email = instance.email,
                        password = instance.password
                    )
                }

                is AuthRootComponent.Child.EmailChoose -> {
                    EmailChooseScreen(component = instance.component)
                }

                is AuthRootComponent.Child.CodeVerifyResetPassword -> {
                    CodeVerifyRPScreen(
                        component = instance.component,
                        email = instance.email,
                    )
                }

                is AuthRootComponent.Child.ResetPassword -> {
                    ResetPasswordScreen(component = instance.component, email = instance.email)
                }
            }
        }
    }
}