package com.miraeldev.navigation.decompose.authComponent

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.miraeldev.navigation.decompose.authComponent.signInComponent.DefaultSignInComponent
import com.miraeldev.navigation.decompose.authComponent.signUpComponent.DefaultSignUpComponent
import kotlinx.serialization.Serializable

class DefaultAuthRootComponent(
    componentContext: ComponentContext
) : AuthRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AuthRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.SignIn,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): AuthRootComponent.Child {
        return when (config) {
            Config.SignIn -> {
                val component = DefaultSignInComponent(
                    componentContext = componentContext,
                    onSignUpClicked = {
                        navigation.push(Config.SignUp)
                    }
                )
                AuthRootComponent.Child.SignIn(component)
            }

            Config.SignUp -> {
                val component = DefaultSignUpComponent(
                    componentContext = componentContext,
                    onSignUpClicked = {},
                    onBackClicked = {
                        navigation.pop()
                    }
                )
                AuthRootComponent.Child.SignUp(component)
            }
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object SignIn : Config

        @Serializable
        data object SignUp : Config
    }

}