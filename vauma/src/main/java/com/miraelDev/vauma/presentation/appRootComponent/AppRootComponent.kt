package com.miraelDev.vauma.presentation.appRootComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraelDev.vauma.navigation.authComponent.AuthRootComponent
import com.miraelDev.vauma.navigation.mainComponent.MainRootComponent
import kotlinx.coroutines.flow.StateFlow

interface AppRootComponent {

    val stack: Value<ChildStack<*, Child>>

    val model: StateFlow<MainStore.State>
    sealed interface Child {

        data class Auth(val component: AuthRootComponent) : Child
        data class Main(val component: MainRootComponent) : Child
        data object Initial : Child
    }
}