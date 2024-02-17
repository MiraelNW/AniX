package com.miraelDev.vauma.navigation.navigationUi

import com.miraelDev.vauma.R

sealed class NavigationItem(
    val id: NavId,
    val title: Int,
    val icon: Int
) {
    data object Home : NavigationItem(
        id = NavId.HOME,
        title = R.string.home,
        icon = R.drawable.ic_home
    )

    data object Search : NavigationItem(
        id = NavId.SEARCH,
        title = R.string.search,
        icon = R.drawable.ic_search
    )

    data object Favourite : NavigationItem(
        id = NavId.FAVOURITE,
        title = R.string.favourite,
        icon =  R.drawable.ic_heart
    )

    data object Account : NavigationItem(
        id = NavId.ACCOUNT,
        title = R.string.account,
        icon = R.drawable.ic_account
    )
}

enum class NavId {
    HOME,
    SEARCH,
    FAVOURITE,
    ACCOUNT,
    UNDEFINED
}
