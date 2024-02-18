package com.miraelDev.vauma.navigation.mainComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraelDev.vauma.navigation.navigationUi.NavId
import com.miraelDev.vauma.presentation.appRootComponent.MainStore
import com.miraeldev.account.presentation.AccountRootComponent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesComponent
import com.miraeldev.animelist.presentation.home.homeComponent.HomeComponent
import com.miraeldev.detailinfo.presentation.detailComponent.DetailComponent
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteComponent
import com.miraeldev.search.presentation.filterScreen.filterComponent.FilterComponent
import com.miraeldev.search.presentation.searchComponent.SearchAnimeComponent
import com.miraeldev.video.presentation.videoComponent.VideoComponent
import kotlinx.coroutines.flow.StateFlow

interface MainRootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onTabNavigate(navId: NavId)

    sealed class Child(val navId: NavId) {
        data class Home(val component: HomeComponent) : Child(NavId.HOME)
        data class Categories(val component: CategoriesComponent, val id: Int) :
            Child(NavId.UNDEFINED)
        data class Search(val component: SearchAnimeComponent, val search: String = "") :
            Child(NavId.SEARCH)
        data class Filter(val component: FilterComponent) : Child(NavId.UNDEFINED)
        data class Favourite(val component: FavouriteComponent) : Child(NavId.FAVOURITE)
        data class Account(val component: AccountRootComponent) : Child(NavId.ACCOUNT)
        data class DetailInfo(val component: DetailComponent, val id: Int) : Child(NavId.UNDEFINED)
        data class VideoScreen(val component: VideoComponent) : Child(NavId.UNDEFINED)

    }
}