package com.miraeldev.animelist.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.miraeldev.animelist.R
import com.miraeldev.animelist.presentation.home.homeComponent.HomeComponent
import com.miraeldev.animelist.presentation.home.homeComponent.HomeStore
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.shimmerlist.ShimmerHome
import com.miraeldev.extensions.NoRippleInteractionSource
import com.miraeldev.extensions.noRippleEffectClick
import com.miraeldev.extensions.pressClickEffect
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.anime.LastWatchedAnime

@Composable
fun HomeScreen(component: HomeComponent, imageLoader: VaumaImageLoader) {

    val model by component.model.collectAsStateWithLifecycle()

    when (val state = model.screenState) {

        is HomeStore.State.HomeScreenState.HomeLoaded -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(bottom = 64.dp)
            ) {
                LastWatchedVideoImage(
                    animeItem = state.user.lastWatchedAnime ?: LastWatchedAnime(-1),
                    imageLoader = imageLoader,
                    onPlayClick = {
                        state.user.lastWatchedAnime?.let {
                            component.loadAnimeVideo(it)
                            component.onPlayClick(it.id)
                        }
                    },
                    addToList = {
                        component.addAnimeToList(
                            state.user,
                            it,
                            state.user.lastWatchedAnime ?: LastWatchedAnime(-1)
                        )
                    },
                    onAnimeClick = component::onAnimeItemClick
                )

                AnimeList(
                    animeList = state.newAnimeList,
                    imageLoader = imageLoader,
                    onAnimeItemClick = component::onAnimeItemClick,
                    listName = "New anime",
                    onSeeAllClick = { component.onSeeAllClick(0) }
                )

                AnimeList(
                    animeList = state.popularAnimeList,
                    imageLoader = imageLoader,
                    onAnimeItemClick = component::onAnimeItemClick,
                    listName = "Popular anime",
                    onSeeAllClick = { component.onSeeAllClick(1) }
                )

                AnimeList(
                    animeList = state.nameAnimeList,
                    imageLoader = imageLoader,
                    onAnimeItemClick = component::onAnimeItemClick,
                    listName = "Name anime",
                    onSeeAllClick = { component.onSeeAllClick(2) }
                )

                AnimeList(
                    animeList = state.filmsAnimeList,
                    imageLoader = imageLoader,
                    onAnimeItemClick = component::onAnimeItemClick,
                    listName = "Films",
                    onSeeAllClick = { component.onSeeAllClick(3) }
                )
            }
        }

        is HomeStore.State.HomeScreenState.Loading -> {
            ShimmerHome()
        }

        is HomeStore.State.HomeScreenState.Initial -> {}
    }
}

@Composable
private fun LastWatchedVideoImage(
    animeItem: LastWatchedAnime,
    imageLoader: VaumaImageLoader,
    onPlayClick: () -> Unit,
    onAnimeClick: (Int) -> Unit,
    addToList: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier.noRippleEffectClick { onAnimeClick(animeItem.id) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .height(400.dp),
            model = imageLoader.load { data(animeItem.imageUrl) },
            contentDescription = "image video",
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 12.dp, bottom = 12.dp)
                .fillMaxHeight(0.4f)
                .fillMaxWidth(0.6f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = animeItem.nameEn,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
            Text(
                text = animeItem.genres.joinToString { it.nameEn },
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PlayButton(onPlayClick = onPlayClick)
                AddToListButton(isFavourite = animeItem.isFavourite, addToList = addToList)
            }
        }
    }
}

@Composable
private fun AnimeList(
    animeList: List<AnimeInfo>,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    listName: String,
    onSeeAllClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = listName,
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.noRippleEffectClick(onClick = onSeeAllClick),
                text = "See all",
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp
            )
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            animeList.forEach {
                AnimeCard(animeItem = it, imageLoader = imageLoader, onAnimeItemClick = onAnimeItemClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(animeItem: AnimeInfo, imageLoader: VaumaImageLoader, onAnimeItemClick: (Int) -> Unit) {
    Card(
        onClick = { onAnimeItemClick(animeItem.id) },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = Modifier.pressClickEffect(),
        elevation = 4.dp
    ) {

        Column {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .height(250.dp)
                        .width(175.dp),
                    model = imageLoader.load { data(animeItem.image.original) },
                    contentDescription = animeItem.nameRu,
                    contentScale = ContentScale.FillBounds
                )
                Rating(animeItem = animeItem)
            }
        }
    }
}

@Composable
private fun Rating(animeItem: AnimeInfo) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(6.dp),
            text = animeItem.score.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun PlayButton(onPlayClick: () -> Unit) {
    OutlinedButton(
        onClick = onPlayClick,
        interactionSource = NoRippleInteractionSource(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = stringResource(R.string.play_icon),
                tint = MaterialTheme.colors.primary
            )
        }

        Text(
            modifier = Modifier.padding(start = 1.dp),
            text = stringResource(R.string.play_text),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun AddToListButton(isFavourite: Boolean, addToList: (Boolean) -> Unit) {

    var isSelected by remember {
        mutableStateOf(isFavourite)
    }

    OutlinedButton(
        onClick = {
            isSelected = !isSelected
            addToList(isSelected)
        },
        interactionSource = NoRippleInteractionSource(),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.White)
    ) {

        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                (
                    fadeIn(animationSpec = tween(200, delayMillis = 100)) +
                        scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(200, delayMillis = 100)
                        )
                    )
                    .togetherWith(fadeOut(animationSpec = tween(400)))
            },
            label = ""
        ) { isSelected ->
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp),
                imageVector = if (isSelected) Icons.Filled.Check else Icons.Filled.Add,
                contentDescription = stringResource(R.string.favourite_button),
                tint = Color.White
            )
        }

        Text(
            modifier = Modifier.padding(1.dp),
            text = stringResource(R.string.favourites),
            color = Color.White,
        )
    }
}