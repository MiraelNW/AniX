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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.animelist.R
import com.miraeldev.exntensions.NoRippleInteractionSource
import com.miraeldev.exntensions.noRippleEffectClick
import com.miraeldev.exntensions.pressClickEffect
import com.miraeldev.presentation.shimmerList.ShimmerHome
import com.miraeldev.user.User
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(
    onAnimeItemClick: (Int) -> Unit,
    onSeeAllClick: (Int) -> Unit,
    onPlayClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val newCategoryList by
    viewModel.newAnimeList.collectAsStateWithLifecycle()

    val filmsAnimeList by
    viewModel.filmsAnimeList.collectAsStateWithLifecycle()

    val popularAnimeList by
    viewModel.popularAnimeList.collectAsStateWithLifecycle()

    val nameAnimeList by
    viewModel.nameAnimeList.collectAsStateWithLifecycle()

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    val addToListAction: (User, Boolean, LastWatchedAnime) -> Unit = remember {
        { user, isSelected, animeItem ->
            viewModel.addAnimeToList(
                user.copy(lastWatchedAnime = user.lastWatchedAnime?.copy(isFavourite = isSelected)),
                isSelected,
                animeItem
            )
        }
    }

    val loadAnimeVideoAction: (LastWatchedAnime) -> Unit =
        remember { { viewModel.loadVideoId(it) } }

    when (val state = screenState) {

        is HomeScreenState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(bottom = 64.dp)
            ) {
                LastWatchedVideoImage(
                    animeItem = state.result.lastWatchedAnime ?: LastWatchedAnime(-1),
                    onPlayClick = {
                        state.result.lastWatchedAnime?.let {
                            loadAnimeVideoAction(it)
                            onPlayClick(it.id)
                        }
                    },
                    addToList = {
                        addToListAction(
                            state.result,
                            it,
                            state.result.lastWatchedAnime ?: LastWatchedAnime(-1)
                        )
                    },
                    onAnimeClick = { id ->
                        onAnimeItemClick(id)
                    }
                )

                AnimeList(
                    animeList = newCategoryList,
                    onAnimeItemClick = onAnimeItemClick,
                    listName = "New anime",
                    onSeeAllClick = { onSeeAllClick(0) }
                )

                AnimeList(
                    animeList = popularAnimeList,
                    onAnimeItemClick = onAnimeItemClick,
                    listName = "Popular anime",
                    onSeeAllClick = { onSeeAllClick(1) }
                )

                AnimeList(
                    animeList = nameAnimeList,
                    onAnimeItemClick = onAnimeItemClick,
                    listName = "Name anime",
                    onSeeAllClick = { onSeeAllClick(2) }
                )

                AnimeList(
                    animeList = filmsAnimeList,
                    onAnimeItemClick = onAnimeItemClick,
                    listName = "Films",
                    onSeeAllClick = { onSeeAllClick(3) }
                )

            }
        }

        is HomeScreenState.Loading -> {
            ShimmerHome()
        }

        is HomeScreenState.Initial -> {

        }

    }


}

@Composable
private fun LastWatchedVideoImage(
    animeItem: LastWatchedAnime,
    onPlayClick: () -> Unit,
    onAnimeClick: (Int) -> Unit,
    addToList: (Boolean) -> Unit
) {

    Box(
        modifier = Modifier.noRippleEffectClick { onAnimeClick(animeItem.id) }
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .height(400.dp),
            imageModel = { animeItem.imageUrl },
            imageOptions = ImageOptions(
                contentDescription = "image video",
                contentScale = ContentScale.FillBounds

            ),
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            },
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
                AnimeCard(animeItem = it, onAnimeItemClick = onAnimeItemClick)
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(animeItem: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {
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

                GlideImage(
                    modifier = Modifier
                        .height(250.dp)
                        .width(175.dp),
                    imageModel = { animeItem.image.original },
                    imageOptions = ImageOptions(
                        contentDescription = animeItem.nameRu,
                        contentScale = ContentScale.FillBounds

                    ),
                    requestOptions = {
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    },
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
                (fadeIn(animationSpec = tween(200, delayMillis = 100)) +
                        scaleIn(
                            initialScale = 0.92f,
                            animationSpec = tween(200, delayMillis = 100)
                        ))
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
            fontSize = 12.sp
        )
    }
}



