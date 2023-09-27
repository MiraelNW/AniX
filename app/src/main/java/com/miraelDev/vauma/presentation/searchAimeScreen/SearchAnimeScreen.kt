package com.miraelDev.vauma.presentation.searchAimeScreen


import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
import com.miraelDev.vauma.R
import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.presentation.animeListScreen.AnimeSearchView
import com.miraelDev.vauma.presentation.commonComposFunc.ErrorAppendItem
import com.miraelDev.vauma.presentation.commonComposFunc.animation.WentWrongAnimation
import com.miraelDev.vauma.presentation.mainScreen.LocalOrientation
import com.miraelDev.vauma.presentation.searchAimeScreen.animeCard.AnimeCard
import com.miraelDev.vauma.presentation.searchAimeScreen.animeCard.LastSearchedAnime
import com.miraelDev.vauma.presentation.shimmerList.ShimmerItem
import com.miraelDev.vauma.presentation.shimmerList.ShimmerList
import io.ktor.utils.io.errors.IOException

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAnimeScreen(
    onFilterClicked: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    viewModel: SearchAnimeViewModel = hiltViewModel(),
) {

    val searchTextState by viewModel.searchTextState
    val searchScreenState by viewModel.screenState.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val filterList by viewModel.filterList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {

        var open by remember { mutableStateOf(false) }

        var isSearchHistoryItemClick by rememberSaveable {
            mutableStateOf(false)
        }

        val filterListState = rememberLazyListState()

        AnimeSearchView(
            textField = searchTextState,
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
            onCloseSearchView = viewModel::showInitialList,
            onFilterClicked = { keyboardController ->
                keyboardController?.hide()
                onFilterClicked()
            }
        )


        when (val results = searchScreenState) {

            is SearchAnimeScreenState.SearchResult -> {
                open = true

                val resultList = results.result.collectAsLazyPagingItems()
                Column {
                    Filters(filterList = filterList, filterListState)
                    SearchResult(
                        searchResults = resultList,
                        onAnimeItemClick = onAnimeItemClick,
                    )
                }

            }

            is SearchAnimeScreenState.InitialList -> {
                open = false
                val resultList = results.result.collectAsLazyPagingItems()
                SearchResult(
                    searchResults = resultList,
                    onAnimeItemClick = onAnimeItemClick,
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
    onAnimeItemClick: (Int) -> Unit,
) {

    Box(Modifier.fillMaxSize()) {

        searchResults.apply {
            when {

                loadState.refresh is LoadState.Loading -> {
                    ShimmerList()
                }


                loadState.refresh is LoadState.Error -> {
                    val e = searchResults.loadState.refresh as LoadState.Error
                    if (e.error is IOException) {
                        WentWrongAnimation(
                            res = R.raw.lost_internet,
                            onClickRetry = searchResults::retry
                        )
                    } else {
                        WentWrongAnimation(
                            res = R.raw.smth_went_wrong,
                            onClickRetry = searchResults::retry
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
                                AnimeCard(
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
                                Box(modifier = Modifier.fillMaxSize()) {
                                    LottieAnimation(
                                        modifier = Modifier
                                            .padding(bottom = 48.dp)
                                            .align(Alignment.Center)
                                            .size(
                                                if (LocalOrientation.current == Configuration.ORIENTATION_LANDSCAPE)
                                                    200.dp
                                                else
                                                    300.dp
                                            ),
                                        composition = composition,
                                        iterations = 4
                                    )

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
                                    onClickRetry = searchResults::retry
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}










