package com.miraelDev.hikari.presentation


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraelDev.hikari.R
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.presentation.AnimeListScreen.AnimeSearchView
import com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard.AnimeCard
import com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard.LastSearchedAnime
import com.miraelDev.hikari.presentation.SearchAimeScreen.SearchAnimeScreenState
import com.miraelDev.hikari.presentation.SearchAimeScreen.SearchAnimeViewModel
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerItem
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerList
import com.miraelDev.hikari.presentation.commonComposFunc.Animation.WentWrongAnimation
import com.miraelDev.hikari.presentation.commonComposFunc.ErrorAppendItem
import io.ktor.utils.io.errors.IOException

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAnimeScreen(
    onFilterClicked: () -> Unit,
    onAnimeItemClick: (Int) -> Unit
) {

    val viewModel = hiltViewModel<SearchAnimeViewModel>()

    val searchTextState by viewModel.searchTextState
    val searchScreenState by viewModel.screenState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val filterList by viewModel.filterList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(bottom = 48.dp),
    ) {

        var open by remember { mutableStateOf(false) }

        var isSearchHistoryItemClick by rememberSaveable {
            mutableStateOf(false)
        }

        var animeList by remember {
            mutableStateOf(listOf<AnimeInfo>())
        }

        val filterListState = rememberLazyListState()

        AnimeSearchView(
            text = searchTextState,
            isSearchHistoryItemClick = isSearchHistoryItemClick,
            showFilter = open,
            open = open,
            onTextChange = viewModel::updateSearchTextState,
            onClearText = viewModel::showSearchHistory,
            onSearchClicked = {
                viewModel.searchAnimeByName(it)
                isSearchHistoryItemClick = false
            },
            clickOnSearchView = viewModel::showSearchHistory,
            onCloseSearchView = viewModel::showStartAnimation,
            onFilterClicked = { keyboardController ->
                keyboardController?.hide()
                onFilterClicked()
            }
        )

        when (val results = searchScreenState) {

            is SearchAnimeScreenState.SearchResult -> {
                open = true
                val resultList = results.result.collectAsLazyPagingItems()
                Column{
                    Filters(filterList = filterList, filterListState)
                    SearchResult(
                        searchResults = resultList,
                        onAnimeItemClick = onAnimeItemClick
                    )
                }

            }

            is SearchAnimeScreenState.InitialList -> {
                open = false
                val resultList = results.result.collectAsLazyPagingItems()
                SearchResult(
                    searchResults = resultList,
                    onAnimeItemClick = onAnimeItemClick
                )

            }

            is SearchAnimeScreenState.SearchHistory -> {
                open = true
                Column {
                    Filters(filterList = filterList, filterListState)
                    SearchHistory(
                            searchHistory = searchHistory,
                            onSearchItemClick = {
                                viewModel.updateSearchTextState(it)
                                isSearchHistoryItemClick = true
                            }
                    )
                }
            }

            is SearchAnimeScreenState.EmptyList -> {}
        }
    }
}



@Composable
private fun Filters(
    filterList: List<String>,
    state: LazyListState
) {
    if (filterList.isNotEmpty()) {
        LazyRow(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(items = filterList, key = { it }) {
                CategoryField(text = it)
            }
        }
    }
}

@Composable
private fun CategoryField(
    text: String
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = text,
            color = MaterialTheme.colors.background,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun SearchHistory(
    searchHistory: List<String>,
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
private fun SearchResult(
    searchResults: LazyPagingItems<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit
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

        ) {
        items(count = searchResults.itemCount) { index ->
            AnimeCard(
                item = checkNotNull(searchResults[index]),
                onAnimeItemClick = onAnimeItemClick
            )
        }

        searchResults.apply {
            when {

                loadState.refresh is LoadState.Loading -> {
                    item { ShimmerList() }
                }

                loadState.append is LoadState.Loading -> {
                    item { ShimmerItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = searchResults.loadState.refresh as LoadState.Error
                    item {
                        if (e.error is IOException) {
                            WentWrongAnimation(
                                res = R.raw.lost_internet,
                                onClickRetry = ::retry
                            )
                        } else {
                            Log.d("tag",e.error.message.toString())
                            WentWrongAnimation(
                                res = R.raw.smth_went_wrong,
                                onClickRetry = ::retry
                            )
                        }
                    }

                }

                loadState.append is LoadState.Error -> {
                    val e = searchResults.loadState.append as LoadState.Error
                    item {
                        if (e.error is IOException) {
                            ErrorAppendItem(
                                message = "Попробуйте снова",
                                onClickRetry = ::retry
                            )
                        }
                    }

                }

            }
        }
    }
}










