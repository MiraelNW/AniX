package com.miraelDev.hikari.presentation.FavouriteListScreen

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.miraelDev.hikari.R
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.entensions.pressClickEffect
import com.miraelDev.hikari.presentation.AnimeListScreen.FavouriteSearchView

@Composable
fun FavouriteListScreen(onAnimeItemClick: (Int) -> Unit) {


    val viewModel = hiltViewModel<FavouriteAnimeViewModel>()

    val searchTextState by viewModel.searchTextState

    val list = mutableListOf<AnimeInfo>().apply {
        repeat(20) {
            add(AnimeInfo(it))
        }
    }

    Column {
        Toolbar(
            text = searchTextState,
            onTextChange = {
                viewModel.updateSearchTextState(it)
            },
            onSearchClicked = {
                //TODO viewmodel
            },
        )
        FavouriteList(favouriteAnimeList = list, onAnimeItemClick = onAnimeItemClick)
    }
}

@Composable
private fun Toolbar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
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
                ){
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary.copy(alpha = 0.1f))
                            .size(32.dp)
                        ,
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_brand_icon),
                        contentDescription ="brand icon",
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
private fun FavouriteList(favouriteAnimeList: List<AnimeInfo>, onAnimeItemClick: (Int) -> Unit) {

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
            AnimeCard(item = it, onAnimeItemClick = onAnimeItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(item: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {

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
                contentDescription = item.nameEn,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Liked",
                tint = Color.Red,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 2.dp, end = 2.dp)

            )
        }
    }
}

