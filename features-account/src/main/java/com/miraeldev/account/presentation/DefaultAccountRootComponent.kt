package com.miraeldev.account.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.miraeldev.account.presentation.screens.account.accountComponent.DefaultAccountComponent
import com.miraeldev.account.presentation.screens.account.accountComponent.Settings
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DefaultDownloadComponent
import com.miraeldev.account.presentation.screens.editProfileScreen.EditProfileComponent.DefaultEditProfileComponent
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.DefaultNotificationComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultAccountRootComponent @AssistedInject constructor(
    private val accountComponentFactory: DefaultAccountComponent.Factory,
    private val editProfileComponentFactory: DefaultEditProfileComponent.Factory,
    private val notificationComponent: DefaultNotificationComponent.Factory,
    private val downloadComponent: DefaultDownloadComponent.Factory,
    @Assisted("onLogOutComplete") private val onLogOutComplete: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : AccountRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AccountRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Account,
        key = "AccountRootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): AccountRootComponent.Child {
        return when (config) {

            is Config.Account -> {
                val component = accountComponentFactory.create(
                    componentContext = componentContext,
                    onSettingItemClick = {
                        when (it) {
                            Settings.EDIT_PROFILE -> navigation.push(Config.EditProfile)
                            Settings.NOTIFICATIONS -> navigation.push(Config.Notifications)
                            Settings.DOWNLOADS -> navigation.push(Config.DownloadSettings)
                            Settings.PRIVACY_POLICY -> navigation.push(Config.EditProfile)
                        }
                    },
                    onLogOutComplete = onLogOutComplete,
                )
                AccountRootComponent.Child.Account(component)
            }

            is Config.EditProfile -> {
                val component = editProfileComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop
                )
                AccountRootComponent.Child.EditProfile(component)
            }

            is Config.Notifications -> {
                val component = notificationComponent.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop
                )
                AccountRootComponent.Child.Notification(component)
            }

            is Config.DownloadSettings -> {
                val component = downloadComponent.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop
                )
                AccountRootComponent.Child.DownloadSettings(component)
            }
//
//            is Config.PrivacyPolicy -> {
//                val component = favouriteComponentFactory.create(
//                    componentContext = componentContext,
//                    onAnimeItemClick = {
//                        navigation.push(Config.DetailInfo(it))
//                    },
//                    navigateToSearchScreen = {
//                        navigation.bringToFront(Config.Search(it))
//                    }
//                )
//                MainRootComponent.Child.Favourite(component)
//            }
        }
    }


    @Serializable
    private sealed interface Config {
        @Serializable
        data object Account : Config

        @Serializable
        data object EditProfile : Config

        @Serializable
        data object Notifications : Config

        @Serializable
        data object DownloadSettings : Config
//        @Serializable
//        data object PrivacyPolicy : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onLogOutComplete") onLogOutComplete: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultAccountRootComponent
    }
}