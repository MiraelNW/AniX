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
import com.miraeldev.account.presentation.DefaultAccountRootComponentFactory
import com.miraeldev.animelist.presentation.categories.categoriesComponent.DefaultCategoriesComponentFactory
import com.miraeldev.animelist.presentation.home.homeComponent.DefaultHomeComponentFactory
import com.miraeldev.detailinfo.presentation.detailComponent.DefaultDetailComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DefaultDetailComponentFactory
import com.miraeldev.favourites.presentation.favouriteComponent.DefaultFavouriteComponentFactory
import com.miraeldev.models.OnLogOut
import com.miraeldev.search.presentation.filterScreen.filterComponent.DefaultFilterComponentFactory
import com.miraeldev.search.presentation.searchComponent.DefaultSearchAnimeComponentFactory
import com.miraeldev.video.presentation.videoComponent.DefaultVideoComponentFactory
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias DefaultMainRootComponentFactory = (ComponentContext, OnLogOut) -> DefaultMainRootComponent
@Inject
class DefaultMainRootComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onLogOutComplete: () -> Unit,
    private val homeComponentFactory: DefaultHomeComponentFactory,
    private val categoriesComponentFactory: DefaultCategoriesComponentFactory,
    private val searchComponentFactory: DefaultSearchAnimeComponentFactory,
    private val filterComponent: DefaultFilterComponentFactory,
    private val favouriteComponentFactory: DefaultFavouriteComponentFactory,
    private val accountRootComponentFactory: DefaultAccountRootComponentFactory,
    private val videoComponent: DefaultVideoComponentFactory,
    private val detailComponentFactory: DefaultDetailComponentFactory
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
                val component = homeComponentFactory(
                    componentContext,
                    {
                        navigation.push(Config.DetailInfo(it))
                    },
                    {
                        navigation.push(Config.Categories(it))
                    },
                    {
                        navigation.push(Config.VideoScreen)
                    }
                )
                MainRootComponent.Child.Home(component)
            }

            is Config.Categories -> {
                val component = categoriesComponentFactory(
                    componentContext
                ) {
                    navigation.push(Config.DetailInfo(it))
                }
                MainRootComponent.Child.Categories(component, config.id)
            }

            is Config.Search -> {
                val component = searchComponentFactory(
                    componentContext,
                    {
                        navigation.push(Config.DetailInfo(it))
                    },
                    {
                        navigation.push(Config.Filter)
                    },
                )
                MainRootComponent.Child.Search(component, config.search)
            }

            is Config.Filter -> {
                val component = filterComponent(
                    componentContext,
                    navigation::pop
                )
                MainRootComponent.Child.Filter(component)
            }

            is Config.Favourite -> {
                val component = favouriteComponentFactory(
                    componentContext,
                    {
                        navigation.push(Config.DetailInfo(it))
                    },
                    {
                        navigation.bringToFront(Config.Search(it))
                    }
                )
                MainRootComponent.Child.Favourite(component)
            }

            is Config.Account -> {
                val component = accountRootComponentFactory(
                    componentContext,
                    onLogOutComplete
                )
                MainRootComponent.Child.Account(component)
            }

            is Config.DetailInfo -> {
                val component = detailComponentFactory(
                    componentContext,
                    navigation::pop,
                    {
                        navigation.push(Config.VideoScreen)
                    },
                    {
                        navigation.push(Config.DetailInfo(it))
                    }
                )
                MainRootComponent.Child.DetailInfo(component, config.id)
            }

            is Config.VideoScreen -> {
                val component = videoComponent(
                    componentContext,
                    navigation::pop
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
