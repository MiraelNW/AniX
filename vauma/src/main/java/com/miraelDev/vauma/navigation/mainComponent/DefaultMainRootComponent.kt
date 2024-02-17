package com.miraelDev.vauma.navigation.mainComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.miraelDev.vauma.navigation.navigationUi.NavId
import com.miraeldev.account.presentation.accountComponent.DefaultAccountComponent
import com.miraeldev.account.presentation.settings.editProfileScreen.EditProfileComponent.DefaultEditProfileComponent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.DefaultCategoriesComponent
import com.miraeldev.animelist.presentation.home.homeComponent.DefaultHomeComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DefaultDetailComponent
import com.miraeldev.favourites.presentation.favouriteComponent.DefaultFavouriteComponent
import com.miraeldev.search.presentation.searchComponent.DefaultSearchAnimeComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultMainRootComponent @AssistedInject constructor(
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("onDarkThemeClick") private val onDarkThemeClick: () -> Unit,
    @Assisted("onLogOutComplete") private val onLogOutComplete: () -> Unit,
    private val homeComponentFactory: DefaultHomeComponent.Factory,
    private val searchComponentFactory: DefaultSearchAnimeComponent.Factory,
    private val categoriesComponentFactory: DefaultCategoriesComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val accountComponentFactory: DefaultAccountComponent.Factory,
    private val detailComponentFactory: DefaultDetailComponent.Factory,
    private val editProfileComponentFactory: DefaultEditProfileComponent.Factory,
) : MainRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, MainRootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        key = "MainRootStack",
        handleBackButton = true,
        childFactory = ::child
    )

    override fun onTabNavigate(navId: NavId) {
        when (navId) {
            NavId.HOME -> {
                navigation.bringToFront(Config.Home)
            }

            NavId.SEARCH -> {
                navigation.bringToFront(Config.Search)
            }

            NavId.FAVOURITE -> {
                navigation.bringToFront(Config.Favourite)
            }

            NavId.ACCOUNT -> {
                navigation.bringToFront(Config.Account)
            }

            NavId.UNDEFINED -> {}
        }
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): MainRootComponent.Child {
        return when (config) {

            is Config.Home -> {
                val component = homeComponentFactory.create(
                    componentContext = componentContext,
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    },
                    onSeeAllClick = {
                        navigation.push(Config.Categories(it))
                    },
                    onPlayClick = {

                    }
                )
                MainRootComponent.Child.Home(component)
            }

            is Config.Search -> {
                val component = searchComponentFactory.create(
                    componentContext = componentContext,
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    },
                    onFilterClicked = {},
                )
                MainRootComponent.Child.Search(component)
            }

            is Config.Categories -> {
                val component = categoriesComponentFactory.create(
                    componentContext = componentContext,
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    }
                )
                MainRootComponent.Child.Categories(component, config.id)
            }

            is Config.Favourite -> {
                val component = favouriteComponentFactory.create(
                    componentContext = componentContext,
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    },
                    navigateToSearchScreen = {

                    }
                )
                MainRootComponent.Child.Favourite(component)
            }

            is Config.Account -> {
                val component = accountComponentFactory.create(
                    componentContext = componentContext,
                    onDarkThemeClick = onDarkThemeClick,
                    onSettingItemClick = {
                        navigation.push(Config.EditProfile)
                    },
                    onLogOutComplete = onLogOutComplete
                )
                MainRootComponent.Child.Account(component)
            }

            is Config.DetailInfo -> {
                val component = detailComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop,
                    onSeriesClick = {

                    },
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    }
                )
                MainRootComponent.Child.DetailInfo(component, config.id)
            }

            is Config.EditProfile -> {
                val component = editProfileComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop
                )
                MainRootComponent.Child.EditProfile(component)
            }
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data object Search : Config

        @Serializable
        data class Categories(val id: Int) : Config

        @Serializable
        data object Account : Config

        @Serializable
        data object Favourite : Config

        @Serializable
        data class DetailInfo(val id: Int) : Config

        @Serializable
        data object EditProfile : Config

    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("onDarkThemeClick") onDarkThemeClick: () -> Unit,
            @Assisted("onLogOutComplete") onLogOutComplete: () -> Unit,
        ): DefaultMainRootComponent
    }
}

