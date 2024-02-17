package com.miraelDev.vauma.navigation.mainComponent

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.miraelDev.vauma.navigation.navigationUi.BottomBar
import com.miraelDev.vauma.navigation.navigationUi.NavigationRailContent
import com.miraeldev.account.presentation.AccountScreen
import com.miraeldev.account.presentation.settings.editProfileScreen.EditProfileScreen
import com.miraeldev.animelist.presentation.categories.AnimeCategoriesScreen
import com.miraeldev.animelist.presentation.home.HomeScreen
import com.miraeldev.detailinfo.presentation.AnimeDetailScreen
import com.miraeldev.favourites.presentation.FavouriteListScreen
import com.miraeldev.search.presentation.SearchAnimeScreen
import com.miraeldev.theme.LocalOrientation

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent(component: MainRootComponent) {

    val orientation = LocalOrientation.current

    var shouldShowBottomBar by remember { mutableStateOf(orientation == Configuration.ORIENTATION_PORTRAIT) }
    var shouldShowNavRail by remember { mutableStateOf(orientation == Configuration.ORIENTATION_LANDSCAPE) }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomBar(component = component)
            }
        }
    ) {
        Row(Modifier.fillMaxSize()) {
            if (shouldShowNavRail) {
                NavigationRailContent(component = component)
            }
            Children(
                stack = component.stack
            ) {
                when (val instance = it.instance) {

                    is MainRootComponent.Child.Home -> {
                        HomeScreen(component = instance.component)
                    }

                    is MainRootComponent.Child.Search -> {
                        SearchAnimeScreen(component = instance.component)
                    }

                    is MainRootComponent.Child.Categories -> {
                        AnimeCategoriesScreen(
                            component = instance.component,
                            categoryId = instance.id
                        )
                    }

                    is MainRootComponent.Child.Favourite -> {
                        FavouriteListScreen(component = instance.component)
                    }

                    is MainRootComponent.Child.Account -> {
                        AccountScreen(component = instance.component)
                    }

                    is MainRootComponent.Child.DetailInfo -> {
                        AnimeDetailScreen(component = instance.component, instance.id)
                    }

                    is MainRootComponent.Child.EditProfile -> {
                        EditProfileScreen(component = instance.component)
                    }
                }
            }
        }
    }
}