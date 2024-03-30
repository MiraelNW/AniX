import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.miraelDev.vauma.navigation.mainComponent.MainRootComponent
import com.miraelDev.vauma.navigation.navigationUi.BottomBar
import com.miraelDev.vauma.navigation.navigationUi.NavigationRailContent
import com.miraeldev.account.presentation.AccountRootContent
import com.miraeldev.animelist.presentation.categories.AnimeCategoriesScreen
import com.miraeldev.animelist.presentation.home.HomeScreen
import com.miraeldev.detailinfo.presentation.AnimeDetailScreen
import com.miraeldev.favourites.presentation.FavouriteListScreen
import com.miraeldev.search.presentation.SearchAnimeScreen
import com.miraeldev.search.presentation.filterScreen.FilterScreen
import com.miraeldev.theme.LocalOrientation
import com.miraeldev.video.presentation.VideoViewScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainContent(component: MainRootComponent, onReadyToDrawStartScreen: () -> Unit) {

    val stack by component.stack.subscribeAsState()

    val orientation = LocalOrientation.current

    var shouldShowBottomBar by remember { mutableStateOf(orientation == Configuration.ORIENTATION_PORTRAIT) }
    var shouldShowNavRail by remember { mutableStateOf(orientation == Configuration.ORIENTATION_LANDSCAPE) }

    LaunchedEffect(Unit) {
        onReadyToDrawStartScreen()
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomBar(component = component, screen = stack.active.instance)
            }
        }
    ) {
        Row(Modifier.fillMaxSize()) {
            if (shouldShowNavRail) {
                NavigationRailContent(component = component, screen = stack.active.instance)
            }
            Children(
                stack = stack
            ) {
                when (val instance = it.instance) {

                    is MainRootComponent.Child.Home -> {
                        HomeScreen(
                            component = instance.component,
                            imageLoader = instance.imageLoader
                        )
                        shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                        shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                    }

                    is MainRootComponent.Child.Categories -> {
                        shouldShowBottomBar = false
                        shouldShowNavRail = false
                        AnimeCategoriesScreen(
                            component = instance.component,
                            categoryId = instance.id,
                            imageLoader = instance.imageLoader
                        )
                    }

                    is MainRootComponent.Child.Search -> {
                        SearchAnimeScreen(
                            component = instance.component,
                            search = instance.search,
                            imageLoader = instance.imageLoader
                        )
                        shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                        shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                    }

                    is MainRootComponent.Child.Filter -> {
                        shouldShowBottomBar = false
                        shouldShowNavRail = false
                        FilterScreen(component = instance.component)
                    }

                    is MainRootComponent.Child.Favourite -> {
                        FavouriteListScreen(component = instance.component, imageLoader = instance.imageLoader)
                        shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                        shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                    }

                    is MainRootComponent.Child.Account -> {
                        AccountRootContent(
                            accountRootComponent = instance.component,
                            hideBottomBar = {
                                shouldShowBottomBar = false
                                shouldShowNavRail = false
                            },
                            showBottomBar = {
                                shouldShowBottomBar =
                                    orientation == Configuration.ORIENTATION_PORTRAIT
                                shouldShowNavRail =
                                    orientation == Configuration.ORIENTATION_LANDSCAPE
                            }
                        )
                        shouldShowBottomBar = orientation == Configuration.ORIENTATION_PORTRAIT
                        shouldShowNavRail = orientation == Configuration.ORIENTATION_LANDSCAPE
                    }

                    is MainRootComponent.Child.DetailInfo -> {
                        shouldShowBottomBar = false
                        shouldShowNavRail = false
                        AnimeDetailScreen(
                            component = instance.component,
                            animeId = instance.id,
                            imageLoader = instance.imageLoader
                        )
                    }

                    is MainRootComponent.Child.VideoScreen -> {
                        shouldShowBottomBar = false
                        shouldShowNavRail = false
                        VideoViewScreen(component = instance.component)
                    }
                }
            }
        }
    }
}

