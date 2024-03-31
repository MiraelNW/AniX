package com.miraeldev.animelist.presentation.categories

import AnimeCard
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.systemGestureExclusion
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.animelist.R
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.ErrorAppendMessage
import com.miraeldev.designsystem.ErrorRetryButton
import com.miraeldev.designsystem.animation.WentWrongAnimation
import com.miraeldev.designsystem.shimmerlist.ShimmerAnimeCard
import com.miraeldev.designsystem.shimmerlist.ShimmerGrid
import com.miraeldev.extensions.NoRippleInteractionSource
import com.miraeldev.theme.LocalOrientation
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

private const val SMALL_ANIMATION = 1.015f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableTabWithViewPager(
    newCategoryList: LazyPagingItems<AnimeInfo>,
    popularAnimeList: LazyPagingItems<AnimeInfo>,
    nameAnimeList: LazyPagingItems<AnimeInfo>,
    filmsAnimeList: LazyPagingItems<AnimeInfo>,
    imageLoader: VaumaImageLoader,
    categoryId: Int,
    onAnimeItemClick: (Int) -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = categoryId,
        initialPageOffsetFraction = 0f,
        pageCount = { 4 }
    )

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

    val categoryList = persistentListOf(
        stringResource(R.string.new_str),
        stringResource(R.string.popular),
        stringResource(R.string.name),
        stringResource(R.string.films)
    )

    ScrollableTabRow(
        modifier = Modifier.height(48.dp),
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
                        else MaterialTheme.colors.onBackground.copy(0.7f),
                    )
                },
                selected = index == pagerState.currentPage,
                interactionSource = NoRippleInteractionSource(),
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
        state = pagerState,
        pageSpacing = 0.dp,
        userScrollEnabled = scrollEnable,
        reverseLayout = false,
        contentPadding = PaddingValues(0.dp),
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
            pagerState, Orientation.Vertical
        ),
        pageContent = { page ->
            when (page) {

                0 -> {
                    AnimeList(
                        categoryList = newCategoryList,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true }
                    )
                }
                1 -> {
                    AnimeList(
                        categoryList = popularAnimeList,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true }
                    )
                }

                2 -> {
                    AnimeList(
                        categoryList = nameAnimeList,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true }
                    )
                }

                3 -> {
                    AnimeList(
                        categoryList = filmsAnimeList,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true }
                    )
                }

                else -> {
                    AnimeList(
                        categoryList = newCategoryList,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true }
                    )
                }
            }
        }
    )
}

@Composable
private fun AnimeList(
    categoryList: LazyPagingItems<AnimeInfo>,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    changeScrollPossibility: (Boolean) -> Unit,
    onClickRetry: () -> Unit
) {

    var shouldShowLoading by remember {
        mutableStateOf(false)
    }

    Box(Modifier.fillMaxSize()) {

        categoryList.apply {
            when {

                loadState.refresh is LoadState.Loading -> {
                    changeScrollPossibility(true)
                    LaunchedEffect(key1 = Unit) {
                        delay(300)
                        shouldShowLoading = true
                    }
                    if (shouldShowLoading) {
                        ShimmerGrid(SMALL_ANIMATION)
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    changeScrollPossibility(false)
                    val e = categoryList.loadState.refresh as LoadState.Error
                    if (e.error is IOException) {
                        WentWrongAnimation(
                            modifier = Modifier.fillMaxSize(),
                            res = R.raw.lost_internet,
                            onClickRetry = onClickRetry
                        )
                    } else {
                        WentWrongAnimation(
                            modifier = Modifier.fillMaxSize(),
                            res = R.raw.smth_went_wrong,
                            onClickRetry = onClickRetry
                        )
                    }

                }

                else -> {

                    val orientation = LocalOrientation.current
                    val cells = remember {
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            3
                        } else {
                            2
                        }
                    }
                    Column {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .navigationBarsPadding(),
                            contentPadding = PaddingValues(
                                top = 12.dp,
                                bottom = if (loadState.append.endOfPaginationReached
                                    || loadState.append is LoadState.Error
                                ) 64.dp else 8.dp,
                                start = 4.dp,
                                end = 4.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 8.dp,
                                alignment = Alignment.CenterVertically
                            ),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            columns = GridCells.Fixed(cells)
                        ) {
                            if (categoryList.itemCount > 0) {
                                items(count = categoryList.itemCount) { index ->
                                    changeScrollPossibility(true)
                                    categoryList[index]?.let {
                                        AnimeCard(
                                            animeItem = it,
                                            imageLoader = imageLoader,
                                            onAnimeItemClick = onAnimeItemClick,
                                        )
                                    }
                                }
                            }

                            if (categoryList.loadState.append is LoadState.Loading) {
                                items(count = 6) {
                                    changeScrollPossibility(true)
                                    ShimmerAnimeCard(
                                        targetValue = SMALL_ANIMATION,
                                        modifier = Modifier
                                    )
                                }
                            }

                            if (categoryList.loadState.append is LoadState.Error) {
                                item {
                                    ErrorAppendMessage(
                                        message = stringResource(
                                            R.string.retry
                                        )
                                    )
                                }
                                item {
                                    ErrorRetryButton(onClickRetry = onClickRetry)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}