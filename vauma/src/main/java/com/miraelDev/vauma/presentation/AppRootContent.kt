package com.miraelDev.vauma.presentation

import MainContent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.miraelDev.vauma.navigation.authComponent.AuthContent
import com.miraelDev.vauma.presentation.appRootComponent.AppRootComponent
import com.miraelDev.vauma.presentation.appRootComponent.DefaultAppRootComponent
import com.miraeldev.theme.VaumaTheme

@Composable
@Suppress("UnstableApi")
fun AppRootContent(
    component: AppRootComponent,
    onReadyToDrawStartScreen: () -> Unit
) {

    val model by component.model.collectAsStateWithLifecycle()

    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, model.isDarkTheme) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !model.isDarkTheme
        )
        onDispose {}
    }


    VaumaTheme(darkTheme = model.isDarkTheme) {
        Children(stack = component.stack) {
            when (val instance = it.instance) {

                is AppRootComponent.Child.Main ->
                    MainContent(component = instance.component, onReadyToDrawStartScreen = onReadyToDrawStartScreen)

                is AppRootComponent.Child.Auth ->
                    AuthContent(component = instance.component, onReadyToDrawStartScreen = onReadyToDrawStartScreen)

                is AppRootComponent.Child.Initial -> {}
            }
        }
    }
}

