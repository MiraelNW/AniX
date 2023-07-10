package com.miraelDev.anix.presentation.MainScreen.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.miraelDev.anix.R
import com.miraelDev.anix.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val title: Int,
    val icon: ImageVector
) {
    object Home : NavigationItem(
        screen = Screen.Home,
        title = R.string.home,
        icon = Icons.Filled.Home
    )

    object Search : NavigationItem(
        screen = Screen.Search,
        title = R.string.search,
        icon = Icons.Filled.Search
    )

    object Library : NavigationItem(
        screen = Screen.Library,
        title = R.string.library,
        icon = Icons.Filled.Favorite
    )
}
