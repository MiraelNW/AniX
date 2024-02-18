package com.miraelDev.vauma.presentation.appRootComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.miraelDev.vauma.navigation.authComponent.DefaultAuthRootComponent
import com.miraelDev.vauma.navigation.mainComponent.DefaultMainRootComponent
import com.miraeldev.extensions.componentScope
import com.miraeldev.models.auth.AuthState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultAppRootComponent @AssistedInject constructor(
    @Assisted("componentContext") componentContext: ComponentContext,
    private val storeFactory: MainStoreFactory,
    private val mainRootComponent: DefaultMainRootComponent.Factory,
    private val authRootComponent: DefaultAuthRootComponent.Factory
) : AppRootComponent, ComponentContext by componentContext {

    private val store: MainStore = instanceKeeper.getStore { storeFactory.create() }

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AppRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Initial,
        key = "AppRootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<MainStore.State> = store.stateFlow

    init {
        store.accept(MainStore.Intent.GetUserAuthState)
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is MainStore.Label.UserAuthStateChecked -> {
                        when (it.authState) {
                            AuthState.Authorized -> navigation.replaceAll(Config.Main)
                            AuthState.NotAuthorized -> navigation.replaceAll(Config.Auth)
                            AuthState.Initial -> {}
                        }
                    }
                }
            }
        }
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): AppRootComponent.Child {
        return when (config) {
            is Config.Auth -> {
                val component = authRootComponent.create(
                    componentContext = componentContext,
                    logIn = { navigation.replaceAll(Config.Main) }
                )
                AppRootComponent.Child.Auth(component)
            }

            is Config.Main -> {
                val component = mainRootComponent.create(
                    componentContext = componentContext,
                    onLogOutComplete = { navigation.replaceAll(Config.Auth) }
                )
                AppRootComponent.Child.Main(component)
            }

            is Config.Initial -> AppRootComponent.Child.Initial
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Auth : Config

        @Serializable
        data object Main : Config

        @Serializable
        data object Initial : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultAppRootComponent
    }
}

