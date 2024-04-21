package com.miraeldev.search.presentation.initialSearchScreen


import AnimeCard
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.ErrorAppendMessage
import com.miraeldev.designsystem.ErrorRetryButton
import com.miraeldev.designsystem.animation.WentWrongAnimation
import com.miraeldev.designsystem.shimmerlist.ShimmerAnimeCard
import com.miraeldev.designsystem.shimmerlist.ShimmerGrid
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingState
import com.miraeldev.search.R
import com.miraeldev.search.presentation.initialSearchScreen.initialSearchComponent.InitialSearchComponent
import com.miraeldev.theme.LocalOrientation
import kotlinx.coroutines.delay
import java.io.IOException


private const val SMALL_ANIMATION = 1.015f


@Composable
fun InitialSearchScreen(
    component: InitialSearchComponent,
    imageLoader: VaumaImageLoader
) {
    val model by component.model.collectAsStateWithLifecycle()

    val lazyGridListState = rememberLazyGridState()

    val shouldPaginate by remember {
        derivedStateOf {
            (lazyGridListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -5) >=
                    (lazyGridListState.layoutInfo.totalItemsCount - 7)
        }
    }

    LaunchedEffect(key1 = shouldPaginate) {
        if (shouldPaginate && model.initialList.loadState == LoadState.REQUEST_INACTIVE) {
            component.loadNextPage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray.copy(alpha = 0.1f))
                .clickable(onClick = component::showSearchHistory)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            text = stringResource(R.string.search_anime)
        )

        InitialAnimeList(
            initialList = model.initialList,
            listState = lazyGridListState,
            onAnimeItemClick = component::onAnimeItemClick,
            imageLoader = imageLoader,
            onClickRetry = {}
        )
    }
}

@Composable
private fun InitialAnimeList(
    initialList: PagingState,
    listState: LazyGridState,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    onClickRetry: () -> Unit
) {

    var shouldShowLoading by remember {
        mutableStateOf(false)
    }

    val orientation = LocalOrientation.current
    val cells = remember {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            3
        } else {
            2
        }
    }
    Box(Modifier.fillMaxSize()) {
        initialList.apply {
            when {

                loadState == LoadState.REFRESH_LOADING -> {
                    LaunchedEffect(key1 = Unit) {
                        delay(300)
                        shouldShowLoading = true
                    }
                    if (shouldShowLoading) {
                        ShimmerGrid(SMALL_ANIMATION)
                    }

                }

                loadState == LoadState.REFRESH_ERROR -> {
                    val e = IOException()
                    if (e is IOException) {
                        WentWrongAnimation(
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .align(Alignment.Center),
                            res = R.raw.lost_internet,
                            onClickRetry = onClickRetry
                        )
                    } else {
                        WentWrongAnimation(
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .align(Alignment.Center),
                            res = R.raw.smth_went_wrong,
                            onClickRetry = onClickRetry
                        )
                    }

                }

                else -> {

                    LazyVerticalGrid(
                        modifier = Modifier.navigationBarsPadding(),
                        contentPadding = PaddingValues(
                            top = 12.dp,
                            bottom = 64.dp,
//                            if (loadState.append.endOfPaginationReached
//                                || loadState.append is LoadState.Error
//                            ) 64.dp else 8.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        columns = GridCells.Fixed(cells),
                        state = listState
                    ) {
                        if (initialList.list.isNotEmpty()) {
                            items(count = initialList.list.size) { index ->
                                AnimeCard(
                                    animeItem = initialList.list[index],
                                    imageLoader = imageLoader,
                                    onAnimeItemClick = onAnimeItemClick
                                )
                            }
                        }

                        if (loadState == LoadState.APPEND_LOADING) {
                            items(count = 4) {
                                ShimmerAnimeCard(
                                    targetValue = SMALL_ANIMATION,
                                    modifier = Modifier
                                )
                            }
                        }

                        if (loadState == LoadState.APPEND_ERROR) {
                            item {
                                ErrorAppendMessage(message = "Попробуйте снова")
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










