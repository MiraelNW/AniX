package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.miraelDev.hikari.R
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.exntensions.pressClickEffect
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerList
import com.miraelDev.hikari.ui.theme.Gold
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableTabWithViewPager(
        screenStateNewAnimeList: State<AnimeListScreenState>,
        screenStateFilmsAnimeList: State<AnimeListScreenState>,
        screenStatePopularAnimeList: State<AnimeListScreenState>,
        screenStateNameAnimeList: State<AnimeListScreenState>,
        viewModel: AnimeListViewModel,
        onAnimeItemClick: (Int) -> Unit
) {

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }

    val categoryList = listOf(
        stringResource(R.string.new_str),
        stringResource(R.string.popular),
        stringResource(R.string.name),
        stringResource(R.string.films)
    )

    ScrollableTabRow(
        modifier = Modifier.height(50.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        edgePadding = 8.dp,
        divider = {},
        indicator = indicator
    ) {
        categoryList.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier.zIndex(6f),
                text = {
                    Text(
                        text = title,
                        color = if (index == pagerState.currentPage) MaterialTheme.colors.onBackground
                        else MaterialTheme.colors.onBackground.copy(0.75f)
                    )
                },
                selected = index == pagerState.currentPage,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
            )
        }
    }

    HorizontalPager(
            modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
                    .systemGestureExclusion(),
            pageCount = categoryList.size,
            state = pagerState
    ) { page ->

        when (page) {
            0 -> {
                AnimeList(
                        screenState = screenStateNewAnimeList,
                        onAnimeItemClick = onAnimeItemClick
                )
            }

            1 -> {
                AnimeList(
                        screenState = screenStatePopularAnimeList,
                        onAnimeItemClick = onAnimeItemClick
                )
            }

            2 -> {
                AnimeList(
                        screenState = screenStateNameAnimeList,
                        onAnimeItemClick = onAnimeItemClick
                )
            }

            3 -> {
                AnimeList(
                        screenState = screenStateFilmsAnimeList,
                        onAnimeItemClick = onAnimeItemClick
                )
            }

            else -> {
                AnimeList(
                        screenState = screenStateNewAnimeList,
                        onAnimeItemClick = onAnimeItemClick
                )
            }
        }
    }
}

@Composable
private fun AnimeList(
        screenState: State<AnimeListScreenState>,
        onAnimeItemClick: (Int) -> Unit
) {
    when (val currentState = screenState.value) {

        is AnimeListScreenState.Loading -> {
            ShimmerList()
        }

        is AnimeListScreenState.Failure -> {
            ShimmerList()
        }

        is AnimeListScreenState.AnimeList -> {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                        contentPadding = PaddingValues(
                                top = 4.dp,
                                bottom = 8.dp,
                                start = 4.dp,
                                end = 4.dp
                        ),
                        state = rememberLazyListState(),
                        modifier = Modifier.navigationBarsPadding(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = currentState.animes, key = { it.id }) {
                        AnimeCard(animeItem = it, onAnimeItemClick = onAnimeItemClick)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AnimeCard(animeItem: AnimeInfo, onAnimeItemClick: (Int) -> Unit) {

    val animatedProgress = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(700)
        )
    }
    val animatedModifier = Modifier.alpha(animatedProgress.value)

    Card(
        onClick = { onAnimeItemClick(animeItem.id) },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .pressClickEffect(),
        elevation = 4.dp
    ) {
        Row(modifier = animatedModifier) {
            AsyncImage(
                model = animeItem.image,
                contentDescription = animeItem.nameEn,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(
                modifier = Modifier
                    .height(150.dp)
                    .width(16.dp)
            )
            AnimePreview(animeItem = animeItem)


        }
    }
}

@Composable
private fun AnimePreview(animeItem: AnimeInfo) {
    Column(
        Modifier.padding(top = 4.dp, end = 8.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = animeItem.nameEn, color = MaterialTheme.colors.onBackground)
            Rating(animeItem = animeItem)
        }
        Text(text = animeItem.nameRu, color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f))
        Spacer(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
        )
        Text(
            text = animeItem.description,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
private fun Rating(animeItem: AnimeInfo) {
    Row {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Rating",
            tint = Gold
        )
        Text(text = animeItem.score.toString(), color = MaterialTheme.colors.onBackground)
    }
}