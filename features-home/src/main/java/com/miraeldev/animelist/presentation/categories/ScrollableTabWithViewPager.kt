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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.animelist.R
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesComponent
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.ErrorAppendMessage
import com.miraeldev.designsystem.ErrorRetryButton
import com.miraeldev.designsystem.animation.WentWrongAnimation
import com.miraeldev.designsystem.shimmerlist.ShimmerAnimeCard
import com.miraeldev.designsystem.shimmerlist.ShimmerGrid
import com.miraeldev.extensions.NoRippleInteractionSource
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingState
import com.miraeldev.theme.LocalOrientation
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SMALL_ANIMATION = 1.015f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableTabWithViewPager(
    component: CategoriesComponent,
    imageLoader: VaumaImageLoader,
    categoryId: Int,
    onAnimeItemClick: (Int) -> Unit
) {
    val model by component.model.collectAsStateWithLifecycle()

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
//            newCategoryList.retry()
//            popularAnimeList.retry()
//            filmsAnimeList.retry()
//            nameAnimeList.retry()
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
                        categoryList = model.newListState,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true },
                        loadNextPage = component::loadNewCategoryNextPage
                    )
                }

                1 -> {
                    AnimeList(
                        categoryList = model.popularListState,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true },
                        loadNextPage = component::loadPopularCategoryNextPage
                    )
                }

                2 -> {
                    AnimeList(
                        categoryList = model.nameListState,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true },
                        loadNextPage = component::loadNameCategoryNextPage
                    )
                }

                3 -> {
                    AnimeList(
                        categoryList = model.filmsListState,
                        imageLoader = imageLoader,
                        onAnimeItemClick = onAnimeItemClick,
                        changeScrollPossibility = { scrollEnable = it },
                        onClickRetry = { shouldRetry = true },
                        loadNextPage = component::loadFilmCategoryNextPage
                    )
                }

                else -> Unit
            }
        }
    )
}

@Composable
private fun AnimeList(
    categoryList: PagingState,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    changeScrollPossibility: (Boolean) -> Unit,
    onClickRetry: () -> Unit,
    loadNextPage: () -> Unit,
) {

    var shouldShowLoading by remember {
        mutableStateOf(false)
    }

    val lazyListListState = rememberLazyListState()

    val shouldPaginate by remember {
        derivedStateOf {
            (lazyListListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -5) >=
                    (lazyListListState.layoutInfo.totalItemsCount - 7)
        }
    }

    LaunchedEffect(key1 = shouldPaginate) {
        if (shouldPaginate && categoryList.loadState == LoadState.REQUEST_INACTIVE) {
            loadNextPage()
        }
    }

    Box(Modifier.fillMaxSize()) {

        categoryList.apply {
            when {

                loadState == LoadState.REFRESH_LOADING -> {
                    changeScrollPossibility(true)
                    LaunchedEffect(key1 = Unit) {
                        delay(300)
                        shouldShowLoading = true
                    }
                    if (shouldShowLoading) {
                        ShimmerGrid(SMALL_ANIMATION)
                    }
                }

                loadState == LoadState.REFRESH_ERROR -> {
                    changeScrollPossibility(false)
                    if (true) {
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
                                bottom = 8.dp,
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
                            if (categoryList.list.isNotEmpty()) {
                                items(count = categoryList.list.size) { index ->
                                    changeScrollPossibility(true)
                                    AnimeCard(
                                        animeItem = categoryList.list[index],
                                        imageLoader = imageLoader,
                                        onAnimeItemClick = onAnimeItemClick,
                                    )
                                }
                            }

                            if (categoryList.loadState == LoadState.APPEND_LOADING) {
                                items(count = 6) {
                                    changeScrollPossibility(true)
                                    ShimmerAnimeCard(
                                        targetValue = SMALL_ANIMATION,
                                        modifier = Modifier
                                    )
                                }
                            }

                            if (categoryList.loadState == LoadState.APPEND_ERROR) {
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