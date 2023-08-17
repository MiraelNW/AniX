package com.miraelDev.hikari.presentation.FavouriteListScreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraelDev.hikari.R
import com.miraelDev.hikari.data.remote.FailureCauses
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.exntensions.pressClickEffect
import com.miraelDev.hikari.presentation.AnimeInfoDetailAndPlay.FavouriteIcon
import com.miraelDev.hikari.presentation.AnimeListScreen.FavouriteSearchView
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerList
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerListFavouriteAnime
import kotlinx.coroutines.delay

@Composable
fun FavouriteListScreen(onAnimeItemClick: (Int) -> Unit) {


    val viewModel = hiltViewModel<FavouriteAnimeViewModel>()

    val searchTextState by viewModel.searchTextState

    val screenState by viewModel.screenState.collectAsState()

    var resultAfterSearch by rememberSaveable { mutableStateOf(false) }

    var isResultEmpty by rememberSaveable { mutableStateOf(false) }

    Column(
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Toolbar(
                text = searchTextState,
                onTextChange = viewModel::updateSearchTextState,
                onSearchClicked = {
                    if (isResultEmpty) {
                        //navigate
                    }
                    resultAfterSearch = true
                    viewModel.searchAnimeItem(it)
                },
                onCloseSearchView = {
//                    Log.d("tag","close")
                    viewModel.loadAnimeList()
                }
        )

        when (val res = screenState) {

            is FavouriteListScreenState.Result -> {
//                Log.d("tag",res.result.toString())
                FavouriteList(
                        favouriteAnimeList = res.result,
                        onAnimeItemClick = onAnimeItemClick,
                        onFavouriteIconClick = viewModel::selectAnimeItem
                )

            }

            is FavouriteListScreenState.Failure -> {

//                Log.d("tag",res.failure.toString())
                if (res.failure is FailureCauses.NotFound) {
                    EmptyListAnimation()
                    ToSearchButton()
                } else {

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

    val scale by animateFloatAsState(targetValue = scaleValue, animationSpec = tween(durationMillis = 300))

    DisposableEffect(key1 = Unit) {

        scaleValue = 1f

        onDispose { scaleValue = 0f }
    }


    Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
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
private fun ToSearchButton() {
    OutlinedButton(
            modifier = Modifier
                    .pressClickEffect(),
            onClick = { /*TODO*/ },
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
            modifier = Modifier.statusBarsPadding(),
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
        onFavouriteIconClick: (Boolean, AnimeInfo) -> Unit
) {

    val configuration = LocalConfiguration.current

    val gridCells = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 4
        else -> 2
    }

    LazyVerticalGrid(
            columns = GridCells.Fixed(gridCells),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 48.dp),
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
        onFavouriteIconClick: (Boolean, AnimeInfo) -> Unit
) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(700)
        )
    }
    val animatedModifier = Modifier.alpha(animatedProgress.value)

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
            AsyncImage(
                    model = item.image,
                    contentDescription = item.nameRu,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(16.dp))
            )
            FavouriteIcon(
                    modifier = animatedModifier
                            .align(Alignment.TopEnd)
                            .padding(end = 8.dp, top = 8.dp),
                    size = 24,
                    selected = true,
                    onFavouriteIconClick = { isSelected ->
                        onFavouriteIconClick(isSelected, item)
                    }
            )
        }
    }
}

