package com.miraeldev.account.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.miraeldev.account.presentation.screens.account.accountComponent.DefaultAccountComponentFactory
import com.miraeldev.account.presentation.screens.downloadSettingsScreen.downloadComponent.DefaultDownloadComponentFactory
import com.miraeldev.account.presentation.screens.editProfileScreen.editProfileComponent.DefaultEditProfileComponentFactory
import com.miraeldev.account.presentation.screens.notificationsScreen.notificationComponent.DefaultNotificationComponentFactory
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.models.OnLogOut
import com.miraeldev.models.anime.Settings
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultAccountRootComponentFactory = (ComponentContext, VaumaImageLoader, OnLogOut) ->
DefaultAccountRootComponent

@Inject
class DefaultAccountRootComponent(
    private val accountComponentFactory: DefaultAccountComponentFactory,
    private val editProfileComponentFactory: DefaultEditProfileComponentFactory,
    private val notificationComponent: DefaultNotificationComponentFactory,
    private val downloadComponent: DefaultDownloadComponentFactory,
    @Assisted componentContext: ComponentContext,
    @Assisted private val imageLoader: VaumaImageLoader,
    @Assisted private val onLogOutComplete: () -> Unit
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
                val component = accountComponentFactory(
                    componentContext,
                    {
                        when (it) {
                            Settings.EDIT_PROFILE -> navigation.push(Config.EditProfile)
                            Settings.NOTIFICATIONS -> navigation.push(Config.Notifications)
                            Settings.DOWNLOADS -> navigation.push(Config.DownloadSettings)
                            Settings.PRIVACY_POLICY -> navigation.push(Config.EditProfile)
                        }
                    },
                    onLogOutComplete,
                )
                AccountRootComponent.Child.Account(component, imageLoader)
            }

            is Config.EditProfile -> {
                val component = editProfileComponentFactory(
                    componentContext,
                    navigation::pop
                )
                AccountRootComponent.Child.EditProfile(component, imageLoader)
            }

            is Config.Notifications -> {
                val component = notificationComponent(
                    componentContext,
                    navigation::pop
                )
                AccountRootComponent.Child.Notification(component)
            }

            is Config.DownloadSettings -> {
                val component = downloadComponent(
                    componentContext,
                    navigation::pop
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
}