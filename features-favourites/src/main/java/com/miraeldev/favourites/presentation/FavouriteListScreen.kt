package com.miraeldev.favourites.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.FavouriteIcon
import com.miraeldev.designsystem.shimmerlist.ShimmerListFavouriteAnime
import com.miraeldev.extensions.pressClickEffect
import com.miraeldev.favourites.R
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteComponent
import com.miraeldev.favourites.presentation.favouriteComponent.FavouriteStore
import com.miraeldev.result.FailureCauses
import com.miraeldev.theme.LocalOrientation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay

@Composable
fun FavouriteListScreen(component: FavouriteComponent, imageLoader: VaumaImageLoader) {
    val model by component.model.collectAsStateWithLifecycle()

    var resultAfterSearch by rememberSaveable { mutableStateOf(false) }

    var isResultEmpty by rememberSaveable { mutableStateOf(false) }

    var isSearchButtonClicked by rememberSaveable { mutableStateOf(false) }

    val searchButtonIsClickedAction = remember { { isSearchButtonClicked = true } }
    val searchButtonIsNotClickedAction = remember { { isSearchButtonClicked = false } }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { isSearchButtonClicked }
            .collect { clicked ->
                if (clicked) {
                    component.loadAnimeList()
                    component.updateSearchTextState("")
                    searchButtonIsNotClickedAction()
                    component.navigateToSearchScreen(model.search)
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Toolbar(
            text = { model.search },
            onTextChange = component::updateSearchTextState,
            onSearchClicked = {
                resultAfterSearch = true
                component.searchAnimeItemInDatabase(it)
            },
            onCloseSearchView = component::loadAnimeList
        )

        when (val res = model.screenState) {

            is FavouriteStore.State.FavouriteListScreenState.Result -> {

                isResultEmpty = false

                FavouriteList(
                    favouriteAnimeList = res.result,
                    imageLoader = imageLoader,
                    onAnimeItemClick = component::onAnimeItemClick,
                    onFavouriteIconClick = component::selectAnimeItem
                )

            }

            is FavouriteStore.State.FavouriteListScreenState.Failure -> {

                if (res.failure is FailureCauses.NotFound) isResultEmpty = true

                if (res.failure is FailureCauses.NotFound && resultAfterSearch) {
                    EmptyListAnimation()
                    ToSearchButton(toSearchButtonClick = searchButtonIsClickedAction)
                } else {
                    EmptyListAnimation()
                }

            }

            is FavouriteStore.State.FavouriteListScreenState.Loading -> {
                ShimmerListFavouriteAnime()
            }

            is FavouriteStore.State.FavouriteListScreenState.Initial -> {}

        }
    }
}

@Composable
private fun EmptyListAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_fav_list))

    var scaleValue by rememberSaveable { mutableStateOf(0f) }

    val scale by animateFloatAsState(
        targetValue = scaleValue,
        animationSpec = tween(durationMillis = 300)
    )

    DisposableEffect(key1 = Unit) {

        scaleValue = 1f

        onDispose { scaleValue = 0f }
    }

    val orientation = LocalOrientation.current

    Box(
        modifier = Modifier
            .fillMaxWidth(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.6f else 1f)
            .fillMaxHeight(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 0.9f else 0.65f)
            .scale(scale)
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomCenter),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
private fun ToSearchButton(toSearchButtonClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.pressClickEffect(),
        onClick = toSearchButtonClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(id = R.string.search_anime),
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
private fun Toolbar(
    text: () -> String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseSearchView: () -> Unit,
) {

    var shouldShowSearchView by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        modifier = if (LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE)
            Modifier.systemBarsPadding()
        else
            Modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
    ) {
        if (shouldShowSearchView) {
            FavouriteSearchView(
                text = text,
                onTextChange = onTextChange,
                onSearchClicked = onSearchClicked,
                onCloseSearchView = {
                    shouldShowSearchView = false
                    onCloseSearchView()
                }
            )
        } else {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .size(32.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_app_icon),
                        contentDescription = stringResource(R.string.brand_icon),
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.favourite),
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 24.sp
                    )
                }
                Row {

                    IconButton(
                        modifier = Modifier
                            .size(28.dp)
                            .padding(2.dp),
                        onClick = {
                            shouldShowSearchView = true
                        }

                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                            contentDescription = stringResource(id = R.string.search),
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .width(12.dp)
                    )
                }

            }
        }
    }
}

@Composable
private fun FavouriteList(
    favouriteAnimeList: ImmutableList<AnimeInfo>,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    onFavouriteIconClick: (AnimeInfo) -> Unit
) {

    val configuration = LocalConfiguration.current

    val gridCells = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

    LazyVerticalGrid(
        modifier = Modifier
            .navigationBarsPadding(),
        columns = GridCells.Fixed(gridCells),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(items = favouriteAnimeList, key = { it.id }) {
            AnimeCard(
                item = it,
                imageLoader = imageLoader,
                onAnimeItemClick = onAnimeItemClick,
                onFavouriteIconClick = onFavouriteIconClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(
    item: AnimeInfo,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    onFavouriteIconClick: (AnimeInfo) -> Unit
) {

//    val animatedProgress = remember { Animatable(initialValue = 0f) }
//    LaunchedEffect(Unit) {
//        animatedProgress.animateTo(
//            targetValue = 1f,
//            animationSpec = tween(700)
//        )
//    }

    var unSelectedItem by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(unSelectedItem) {
        if (unSelectedItem) {
            delay(400)
            onFavouriteIconClick(item)
        }
    }
//    val animatedModifier = Modifier.alpha(animatedProgress.value)

    AnimatedVisibility(
        visible = !unSelectedItem,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            onClick = { onAnimeItemClick(item.id) },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .pressClickEffect(),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier) {
                AsyncImage(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    model = imageLoader.load { data(item.image.original) },
                    contentDescription = item.nameRu
                )
                FavouriteIcon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 8.dp),
                    size = 24,
                    selected = true,
                    onFavouriteIconClick = { _ ->
                        unSelectedItem = true
                    }
                )
            }
        }
    }


}

