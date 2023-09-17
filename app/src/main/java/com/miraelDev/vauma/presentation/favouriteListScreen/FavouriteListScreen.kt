package com.miraelDev.vauma.presentation.favouriteListScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraelDev.vauma.R
import com.miraelDev.vauma.data.remote.FailureCauses
import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.exntensions.pressClickEffect
import com.miraelDev.vauma.navigation.Screen
import com.miraelDev.vauma.presentation.animeInfoDetailAndPlay.FavouriteIcon
import com.miraelDev.vauma.presentation.animeListScreen.FavouriteSearchView
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation
import com.miraelDev.vauma.presentation.shimmerList.ShimmerListFavouriteAnime
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay

@Composable
fun FavouriteListScreen(
    onAnimeItemClick: (Int) -> Unit,
    navigateToSearchScreen: (String) -> Unit,
    viewModel: FavouriteAnimeViewModel = hiltViewModel(),
) {

    val searchTextState by viewModel.searchTextState

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    var resultAfterSearch by rememberSaveable { mutableStateOf(false) }

    var isResultEmpty by rememberSaveable { mutableStateOf(false) }

    var isSearchButtonClicked by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isSearchButtonClicked) {
        if (isSearchButtonClicked) {
            viewModel.searchAnimeByName(searchTextState)
            delay(800)
            viewModel.loadAnimeList()
            navigateToSearchScreen(Screen.SearchAndFilter.route)
            viewModel.updateSearchTextState("")
        }
        isSearchButtonClicked = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Toolbar(
            text = searchTextState,
            onTextChange = viewModel::updateSearchTextState,
            onSearchClicked = {
                resultAfterSearch = true
                viewModel.searchAnimeItemInDatabase(it)
            },
            onCloseSearchView = viewModel::loadAnimeList
        )

        when (val res = screenState) {

            is FavouriteListScreenState.Result -> {

                isResultEmpty = false

                FavouriteList(
                    favouriteAnimeList = res.result,
                    onAnimeItemClick = onAnimeItemClick,
                    onFavouriteIconClick = viewModel::selectAnimeItem,
                    nextDataIsNeed = viewModel::loadAnimeList
                )

            }

            is FavouriteListScreenState.Failure -> {

                if (res.failure is FailureCauses.NotFound) isResultEmpty = true

                if (res.failure is FailureCauses.NotFound && resultAfterSearch) {
                    EmptyListAnimation()
                    ToSearchButton(toSearchButtonClick = { isSearchButtonClicked = true })
                } else {
                    EmptyListAnimation()
                }

            }

            is FavouriteListScreenState.Loading -> {
                ShimmerListFavouriteAnime()
            }

            is FavouriteListScreenState.Initial -> {}

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
            .fillMaxWidth(if(orientation == Configuration.ORIENTATION_LANDSCAPE) 0.6f else 1f)
            .fillMaxHeight(if(orientation == Configuration.ORIENTATION_LANDSCAPE) 0.9f else 0.65f)
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
        modifier = Modifier
            .pressClickEffect(),
        onClick = toSearchButtonClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.primary
        )
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Найти аниме",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
private fun Toolbar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseSearchView: () -> Unit,
) {

    var shouldShowSearchView by remember {
        mutableStateOf(false)
    }

    TopAppBar(
        modifier =if(LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE)
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
                            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                            .size(32.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_brand_icon),
                        contentDescription = "brand icon",
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colors.primary,
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
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
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
    favouriteAnimeList: List<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit,
    onFavouriteIconClick: (AnimeInfo) -> Unit,
    nextDataIsNeed: () -> Unit,
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
    onAnimeItemClick: (Int) -> Unit,
    onFavouriteIconClick: (AnimeInfo) -> Unit
) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(700)
        )
    }

    var unSelectedItem by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(unSelectedItem) {
        if (unSelectedItem) {
            delay(400)
            onFavouriteIconClick(item)
        }
    }
    val animatedModifier = Modifier.alpha(animatedProgress.value)

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
            Box(modifier = animatedModifier) {
                GlideImage(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    imageModel = { item.image },
                    imageOptions = ImageOptions(
                        contentDescription = item.nameRu,
                    ),
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    },
                )
                FavouriteIcon(
                    modifier = animatedModifier
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

