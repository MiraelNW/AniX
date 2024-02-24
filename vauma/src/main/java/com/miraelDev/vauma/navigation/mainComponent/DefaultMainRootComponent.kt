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
import com.miraeldev.account.presentation.DefaultAccountRootComponent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.DefaultCategoriesComponent
import com.miraeldev.animelist.presentation.home.homeComponent.DefaultHomeComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DefaultDetailComponent
import com.miraeldev.favourites.presentation.favouriteComponent.DefaultFavouriteComponent
import com.miraeldev.search.presentation.filterScreen.filterComponent.DefaultFilterComponent
import com.miraeldev.search.presentation.searchComponent.DefaultSearchAnimeComponent
import com.miraeldev.video.presentation.videoComponent.DefaultVideoComponent
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class DefaultMainRootComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onLogOutComplete: () -> Unit,
    private val homeComponentFactory: DefaultHomeComponent.Factory,
    private val categoriesComponentFactory: DefaultCategoriesComponent.Factory,
    private val searchComponentFactory: DefaultSearchAnimeComponent.Factory,
    private val filterComponent: DefaultFilterComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val accountRootComponentFactory: DefaultAccountRootComponent.Factory,
    private val videoComponent: DefaultVideoComponent.Factory,
    private val detailComponentFactory: DefaultDetailComponent.Factory
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
                navigation.bringToFront(Config.Search())
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
                        navigation.push(Config.VideoScreen)
                    }
                )
                MainRootComponent.Child.Home(component)
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

            is Config.Search -> {
                val component = searchComponentFactory.create(
                    componentContext = componentContext,
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    },
                    onFilterClicked = {
                        navigation.push(Config.Filter)
                    },
                )
                MainRootComponent.Child.Search(component, config.search)
            }

            is Config.Filter -> {
                val component = filterComponent.create(
                    componentContext = componentContext,
                    onBackPressed = navigation::pop
                )
                MainRootComponent.Child.Filter(component)
            }

            is Config.Favourite -> {
                val component = favouriteComponentFactory.create(
                    componentContext = componentContext,
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    },
                    navigateToSearchScreen = {
                        navigation.bringToFront(Config.Search(it))
                    }
                )
                MainRootComponent.Child.Favourite(component)
            }

            is Config.Account -> {
                val component = accountRootComponentFactory.create(
                    componentContext = componentContext,
                    onLogOutComplete = onLogOutComplete
                )
                MainRootComponent.Child.Account(component)
            }

            is Config.DetailInfo -> {
                val component = detailComponentFactory.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop,
                    onSeriesClick = {
                        navigation.push(Config.VideoScreen)
                    },
                    onAnimeItemClick = {
                        navigation.push(Config.DetailInfo(it))
                    }
                )
                MainRootComponent.Child.DetailInfo(component, config.id)
            }

            is Config.VideoScreen -> {
                val component = videoComponent.create(
                    componentContext = componentContext,
                    onBackClicked = navigation::pop
                )
                MainRootComponent.Child.VideoScreen(component)
            }
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data class Search(val search: String = "") : Config

        @Serializable
        data object Filter : Config

        @Serializable
        data class Categories(val id: Int) : Config

        @Serializable
        data object Account : Config

        @Serializable
        data object Favourite : Config

        @Serializable
        data class DetailInfo(val id: Int) : Config
        @Serializable
        data object VideoScreen : Config

    }


}

