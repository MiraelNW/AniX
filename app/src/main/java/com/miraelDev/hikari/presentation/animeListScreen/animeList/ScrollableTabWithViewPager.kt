package com.miraelDev.hikari.presentation.animeListScreen.animeList

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miraelDev.hikari.R
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.exntensions.pressClickEffect
import com.miraelDev.hikari.presentation.shimmerList.ShimmerItem
import com.miraelDev.hikari.presentation.shimmerList.ShimmerList
import com.miraelDev.hikari.presentation.commonComposFunc.animation.WentWrongAnimation
import com.miraelDev.hikari.presentation.commonComposFunc.ErrorAppendItem
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableTabWithViewPager(
    newCategoryList: LazyPagingItems<AnimeInfo>,
    filmsAnimeList: LazyPagingItems<AnimeInfo>,
    popularAnimeList: LazyPagingItems<AnimeInfo>,
    nameAnimeList: LazyPagingItems<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit
) {

    val pagerState = rememberPagerState()

    val coroutineScope = rememberCoroutineScope()

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }

    var scrollEnable by rememberSaveable { mutableStateOf(true) }

    var shouldRetry by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = shouldRetry) {
        if (shouldRetry) {
            newCategoryList.retry()
            popularAnimeList.retry()
            filmsAnimeList.retry()
            nameAnimeList.retry()
            shouldRetry = false
        }
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
                enabled = scrollEnable,
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
        state = pagerState,
        userScrollEnabled = scrollEnable
    ) { page ->

        when (page) {

            0 -> {
                AnimeList(
                    categoryList = newCategoryList,
                    onAnimeItemClick = onAnimeItemClick,
                    changeScrollPossibility = { scrollEnable = it },
                    onClickRetry = { shouldRetry = true }
                )
            }

            1 -> {

                AnimeList(
                    categoryList = popularAnimeList,
                    onAnimeItemClick = onAnimeItemClick,
                    changeScrollPossibility = { scrollEnable = it },
                    onClickRetry = { shouldRetry = true }
                )
            }

            2 -> {
                AnimeList(
                    categoryList = nameAnimeList,
                    onAnimeItemClick = onAnimeItemClick,
                    changeScrollPossibility = { scrollEnable = it },
                    onClickRetry = { shouldRetry = true }
                )
            }

            3 -> {

                AnimeList(
                    categoryList = filmsAnimeList,
                    onAnimeItemClick = onAnimeItemClick,
                    changeScrollPossibility = { scrollEnable = it },
                    onClickRetry = { shouldRetry = true }
                )
            }

            else -> {
                AnimeList(
                    categoryList = newCategoryList,
                    onAnimeItemClick = onAnimeItemClick,
                    changeScrollPossibility = { scrollEnable = it },
                    onClickRetry = { shouldRetry = true }
                )
            }
        }
    }
}

@Composable
private fun AnimeList(
    categoryList: LazyPagingItems<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit,
    changeScrollPossibility: (Boolean) -> Unit,
    onClickRetry: () -> Unit
) {

    val scrollState = rememberLazyListState()

//    Log.d("tag", categoryList.loadState.toString())

    Box(Modifier.fillMaxSize()) {

        categoryList.apply {
            when {

                loadState.refresh is LoadState.Loading -> {
                    changeScrollPossibility(true)
                    ShimmerList()
                }

                loadState.refresh is LoadState.Error -> {
                    changeScrollPossibility(false)
                    val e = categoryList.loadState.refresh as LoadState.Error
                    if (e.error is IOException) {
                        WentWrongAnimation(
                            res = R.raw.lost_internet,
                            onClickRetry = onClickRetry
                        )
                    } else {
                        WentWrongAnimation(
                            res = R.raw.smth_went_wrong,
                            onClickRetry = onClickRetry
                        )
                    }

                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.navigationBarsPadding(),
                        contentPadding = PaddingValues(
                            top = 4.dp,
                            bottom = 8.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        state = scrollState,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        if (categoryList.itemCount > 0) {
                            items(count = categoryList.itemCount) { index ->
                                changeScrollPossibility(true)
                                categoryList[index]?.let {
                                    AnimeCard(
                                        animeItem = it,
                                        onAnimeItemClick = onAnimeItemClick
                                    )
                                }
                            }
                        }

                        if (categoryList.loadState.append is LoadState.Loading) {
                            item {
                                changeScrollPossibility(true)
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    repeat(4) {
                                        ShimmerItem()
                                    }
                                }
                            }
                        }

                        if (categoryList.loadState.append is LoadState.Error) {
                            item {
                                ErrorAppendItem(
                                    message = "Попробуйте снова",
                                    onClickRetry = onClickRetry
                                )
                            }
                        }
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
            animationSpec = tween(500)
        )
    }

    val animatedModifier = Modifier
        .alpha(animatedProgress.value)

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
            Box(
                modifier = Modifier
                    .height(220.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxWidth(),
                    imageModel = { animeItem.image },
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

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .width(16.dp)
            )
            AnimePreview(animeItem = animeItem)
        }
    }
}

@Composable
private fun AnimePreview(
    animeItem: AnimeInfo
) {
    Column(
        modifier = Modifier
            .height(220.dp)
            .padding(top = 8.dp, end = 12.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 16.dp),
                text = animeItem.nameRu,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = animeItem.nameEn,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = animeItem.genres.joinToString(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = animeItem.airedOn.take(4),
                maxLines = 1,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onBackground
            )
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