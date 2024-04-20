package com.miraelDev.vauma.navigation.mainComponent

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.miraelDev.vauma.navigation.navigationUi.NavId
import com.miraeldev.account.presentation.AccountRootComponent
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesComponent
import com.miraeldev.animelist.presentation.home.homeComponent.HomeComponent
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.detailinfo.presentation.detailComponent.DetailComponent
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteComponent
import com.miraeldev.search.presentation.filterScreen.filterComponent.FilterComponent
import com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent.InitialSearchComponent
import com.miraeldev.search.presentation.searchHistoryScreen.searchHistoryComponent.SearchHistoryComponent
import com.miraeldev.search.presentation.searchResultsScreen.searchResultsComponent.SearchResultsComponent
import com.miraeldev.video.presentation.videoComponent.VideoComponent
import com.miraeldev.api.VaumaImageLoader as Loader

interface MainRootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onTabNavigate(navId: NavId)

    sealed class Child(val navId: NavId) {
        data class Home(val component: HomeComponent, val imageLoader: Loader) :
            Child(NavId.HOME)
        data class Categories(val component: CategoriesComponent, val id: Int, val imageLoader: Loader) :
            Child(NavId.UNDEFINED)
        data class InitialSearch(val component: InitialSearchComponent, val imageLoader: VaumaImageLoader) : Child(NavId.SEARCH)
        data class SearchHistory(val component: SearchHistoryComponent) : Child(NavId.SEARCH)
        data class SearchResults(
            val component: SearchResultsComponent,
            val search: String = "",
            val imageLoader: VaumaImageLoader
        ) : Child(NavId.SEARCH)
        data class Filter(val component: FilterComponent) : Child(NavId.UNDEFINED)
        data class Favourite(val component: FavouriteComponent, val imageLoader: Loader) : Child(NavId.FAVOURITE)
        data class Account(val component: AccountRootComponent) : Child(NavId.ACCOUNT)
        data class DetailInfo(
            val component: DetailComponent,
            val id: Int,
            val imageLoader: Loader,
        ) : Child(NavId.UNDEFINED)
        data class VideoScreen(val component: VideoComponent) : Child(NavId.UNDEFINED)

    }
}