package com.miraeldev.search.presentation


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.presentation.AnimeCard
import com.miraeldev.presentation.ErrorAppendItem
import com.miraeldev.presentation.shimmerList.ShimmerAnimeCard
import com.miraeldev.presentation.shimmerList.ShimmerGrid
import com.miraeldev.presentation.shimmerList.ShimmerItem
import com.miraeldev.search.R
import com.miraeldev.search.presentation.animeCard.LastSearchedAnime
import com.miraeldev.search.presentation.animeCard.SearchAnimeCard
import com.miraeldev.search.presentation.searchComponent.SearchAnimeComponent
import com.miraeldev.search.presentation.searchComponent.SearchAnimeStore
import com.miraeldev.theme.LocalOrientation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import java.io.IOException


private const val SMALL_ANIMATION = 1.015f


@Composable
fun SearchAnimeScreen(component: SearchAnimeComponent, search: String = "") {
    val model by component.model.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {

        var open by rememberSaveable { mutableStateOf(false) }

        var isSearchHistoryItemClick by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            if (search.isEmpty()) {
                component.showInitialList()
            } else {
                component.onSearchChange(search)
                component.searchAnimeByName(search)
            }
        }

        AnimeSearchView(
            text = { model.search },
            isSearchHistoryItemClick = isSearchHistoryItemClick,
            showFilter = open,
            onTextChange = component::onSearchChange,
            onClearText = component::showSearchHistory,
            onSearchClicked = {
                component.searchAnimeByName(it)
                isSearchHistoryItemClick = false
            },
            clickOnSearchView = component::showSearchHistory,
            onCloseSearchView = component::showInitialList,
            onFilterClicked = component::onFilterClicked
        )

        when (val results = model.screenState) {

            is SearchAnimeStore.State.SearchAnimeScreenState.SearchResult -> {
                open = true

                val resultList = results.result.collectAsLazyPagingItems()
                Column {
                    Filters(filterList = model.filterList)
                    SearchResult(
                        searchResults = resultList,
                        onAnimeItemClick = component::onAnimeItemClick,
                        onRetry = resultList::retry
                    )
                }

            }

            is SearchAnimeStore.State.SearchAnimeScreenState.InitialList -> {

                open = false
                val resultList = results.result.collectAsLazyPagingItems()
                InitialAnimeList(
                    initialList = resultList,
                    onAnimeItemClick = component::onAnimeItemClick,
                    onClickRetry = resultList::retry
                )

            }

            is SearchAnimeStore.State.SearchAnimeScreenState.SearchHistory -> {
                open = true
                Column {
                    Filters(filterList = model.filterList)
                    SearchHistory(
                        searchHistory = model.searchHistory,
                        onSearchItemClick = {
                            component.onSearchChange(it)
                            isSearchHistoryItemClick = true
                        }
                    )
                }
            }

            is SearchAnimeStore.State.SearchAnimeScreenState.EmptyList -> {}
        }
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
private fun SearchHistory(
    searchHistory: ImmutableList<String>,
    onSearchItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 8.dp,
            start = 8.dp,
            bottom = 8.dp,
            end = 8.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = searchHistory, key = { it }) {
            LastSearchedAnime(searchName = it, onSearchItemClick = onSearchItemClick)
        }
    }
}

@Composable
private fun InitialAnimeList(
    initialList: LazyPagingItems<AnimeInfo>,
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

                loadState.refresh is LoadState.Loading -> {
                    LaunchedEffect(key1 = Unit) {
                        delay(300)
                        shouldShowLoading = true
                    }
                    if (shouldShowLoading) {
                        ShimmerGrid(SMALL_ANIMATION)
                    }

                }

                loadState.refresh is LoadState.Error -> {
                    val e = initialList.loadState.refresh as LoadState.Error
                    if (e.error is IOException) {
                        com.miraeldev.presentation.animation.WentWrongAnimation(
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .align(Alignment.Center),
                            res = R.raw.lost_internet,
                            onClickRetry = onClickRetry
                        )
                    } else {
                        com.miraeldev.presentation.animation.WentWrongAnimation(
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
                        if (initialList.itemCount > 0) {
                            items(count = initialList.itemCount) { index ->
                                initialList[index]?.let {
                                    AnimeCard(
                                        animeItem = it,
                                        onAnimeItemClick = onAnimeItemClick
                                    )
                                }
                            }
                        }

                        if (initialList.loadState.append is LoadState.Loading) {
                            items(count = 6) {
                                ShimmerAnimeCard(
                                    targetValue = SMALL_ANIMATION,
                                    modifier = Modifier
                                )
                            }
                        }

                        if (initialList.loadState.append is LoadState.Error) {
                            item {
                                com.miraeldev.presentation.ErrorAppendMessage(message = "Попробуйте снова")
                            }
                            item {
                                com.miraeldev.presentation.ErrorRetryButton(onClickRetry = onClickRetry)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResult(
    searchResults: LazyPagingItems<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit,
    onRetry: () -> Unit,
) {

    var shouldShowLoading by remember {
        mutableStateOf(false)
    }

    Box(Modifier.fillMaxSize()) {

        searchResults.apply {
            when {

                loadState.refresh is LoadState.Loading -> {
                    LaunchedEffect(key1 = Unit) {
                        delay(300)
                        shouldShowLoading = true
                    }
                    if (shouldShowLoading) {
                        ShimmerGrid(targetValue = 1.02f)
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = searchResults.loadState.refresh as LoadState.Error
                    if (e.error is IOException) {
                        com.miraeldev.presentation.animation.WentWrongAnimation(
                            modifier = Modifier.fillMaxSize(),
                            res = R.raw.lost_internet,
                            onClickRetry = onRetry
                        )
                    } else {
                        com.miraeldev.presentation.animation.WentWrongAnimation(
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
                            bottom = if (loadState.append.endOfPaginationReached) 64.dp else 8.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Top
                        )
                    ) {

                        items(count = searchResults.itemCount) { index ->

                            searchResults[index]?.let {
                                SearchAnimeCard(
                                    item = it,
                                    onAnimeItemClick = onAnimeItemClick
                                )
                            }
                        }

                        if (
                            searchResults.loadState.append.endOfPaginationReached &&
                            searchResults.loadState.prepend.endOfPaginationReached &&
                            searchResults.itemCount == 0
                        ) {
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

                        if (searchResults.loadState.append is LoadState.Loading) {
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

                        if (searchResults.loadState.append is LoadState.Error) {
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










