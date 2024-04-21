package com.miraeldev.search.presentation.searchResultsScreen


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraeldev.api.VaumaImageLoader
import com.miraeldev.designsystem.ErrorAppendItem
import com.miraeldev.designsystem.animation.WentWrongAnimation
import com.miraeldev.designsystem.shimmerlist.ShimmerGrid
import com.miraeldev.designsystem.shimmerlist.ShimmerItem
import com.miraeldev.models.paging.LoadState
import com.miraeldev.models.paging.PagingState
import com.miraeldev.search.R
import com.miraeldev.search.presentation.animeCard.SearchAnimeCard
import com.miraeldev.search.presentation.searchResultsScreen.searchResultsComponent.SearchResultsComponent
import com.miraeldev.theme.LocalOrientation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import java.io.IOException

@Composable
fun SearchResultsScreen(
    component: SearchResultsComponent,
    animeName: String,
    imageLoader: VaumaImageLoader
) {
    val model by component.model.collectAsStateWithLifecycle()

    val open by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        AnimeSearchView(
            text = animeName,
            showFilter = open,
            onShowSearchHistory = component::showSearchHistory,
            onFilterClicked = component::onFilterClicked
        )
        Filters(filterList = model.filterList)
        SearchResult(
            searchResults = model.searchResults,
            imageLoader = imageLoader,
            onAnimeItemClick = component::onAnimeItemClick,
            onRetry = {}
        )
    }
}


@Composable
private fun Filters(filterList: ImmutableList<String>) {
    if (filterList.isNotEmpty()) {
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(items = filterList, key = { it }) {
                CategoryField(text = { it })
            }
        }
    }
}

@Composable
private fun CategoryField(
    text: () -> String
) {

    val value = remember(text) { text() }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = value,
            color = MaterialTheme.colors.background,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 14.sp
        )
    }
}


@Composable
private fun SearchResult(
    searchResults: PagingState,
    imageLoader: VaumaImageLoader,
    onAnimeItemClick: (Int) -> Unit,
    onRetry: () -> Unit,
) {

    var shouldShowLoading by remember {
        mutableStateOf(false)
    }

    Box(Modifier.fillMaxSize()) {

        searchResults.apply {
            when {
                loadState == LoadState.REFRESH_LOADING -> {
                    LaunchedEffect(key1 = Unit) {
                        delay(300)
                        shouldShowLoading = true
                    }
                    if (shouldShowLoading) {
                        ShimmerGrid(targetValue = 1.02f)
                    }
                }

                loadState == LoadState.REFRESH_ERROR -> {
                    if (true) {
                        WentWrongAnimation(
                            modifier = Modifier.fillMaxSize(),
                            res = R.raw.lost_internet,
                            onClickRetry = onRetry
                        )
                    } else {
                        WentWrongAnimation(
                            modifier = Modifier.fillMaxSize(),
                            res = R.raw.smth_went_wrong,
                            onClickRetry = onRetry
                        )
                    }

                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding(),
                        contentPadding = PaddingValues(
                            top = 4.dp,
                            bottom = 8.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Top
                        )
                    ) {

                        items(count = searchResults.list.size) { index ->
                            SearchAnimeCard(
                                item = searchResults.list[index],
                                imageLoader = imageLoader,
                                onAnimeItemClick = onAnimeItemClick
                            )
                        }

                        if (searchResults.loadState == LoadState.EMPTY) {
                            item {
                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(R.raw.search)
                                )
                                Column(
                                    modifier = Modifier.fillParentMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    LottieAnimation(
                                        modifier = Modifier
                                            .padding(bottom = 36.dp)
                                            .size(
                                                if (LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE)
                                                    200.dp
                                                else
                                                    300.dp
                                            ),
                                        composition = composition,
                                        iterations = 16
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(24.dp))
                                            .background(MaterialTheme.colors.primary)
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(
                                                horizontal = 12.dp,
                                                vertical = 8.dp
                                            ),
                                            text = "Не смогли найти это аниме",
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    }


                                }
                            }
                        }

                        if (searchResults.loadState == LoadState.APPEND_LOADING) {
                            item {
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

                        if (searchResults.loadState == LoadState.APPEND_ERROR) {
                            item {
                                ErrorAppendItem(
                                    modifier = Modifier.padding(bottom = 64.dp),
                                    message = stringResource(R.string.try_again),
                                    onClickRetry = onRetry
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}










