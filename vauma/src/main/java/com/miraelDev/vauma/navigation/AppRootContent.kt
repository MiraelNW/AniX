package com.miraelDev.vauma.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.miraelDev.vauma.navigation.authComponent.AuthContent
import com.miraelDev.vauma.navigation.mainComponent.MainContent

@Composable
fun AppRootContent(
    component: DefaultAppRootComponent,
    onReadyToDrawStartScreen: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        onReadyToDrawStartScreen()
    }

    Children(stack = component.stack) {
        when (val instance = it.instance) {

            is AppRootComponent.Child.Main -> MainContent(component = instance.component)

            is AppRootComponent.Child.Auth -> AuthContent(component = instance.component)
        }
    }
}

