package com.miraelDev.hikari.presentation


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miraelDev.hikari.R
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard.AnimeCard
import com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard.LastSearchedAnime
import com.miraelDev.hikari.presentation.AnimeListScreen.AnimeSearchView
import com.miraelDev.hikari.presentation.SearchAimeScreen.SearchAnimeScreenState
import com.miraelDev.hikari.presentation.SearchAimeScreen.SearchAnimeViewModel
import com.miraelDev.hikari.presentation.ShimmerList.ShimmerList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAnimeScreen(
        onFilterClicked: () -> Unit,
        onAnimeItemClick: (Int) -> Unit
) {

    val viewModel = hiltViewModel<SearchAnimeViewModel>()

    val searchTextState by viewModel.searchTextState
    val filterList by viewModel.filterList.collectAsState(listOf())
    val searchState by viewModel.animeBySearch.collectAsState(SearchAnimeScreenState.Initial)
    val searchHistory by viewModel.searchHistory.collectAsState()
    Column(
            modifier = Modifier
                    .systemBarsPadding()
                    .padding(bottom = 48.dp),
    ) {

        var showStartAnimation by rememberSaveable {
            mutableStateOf(true)
        }

        var openSearchHistory by rememberSaveable {
            mutableStateOf(false)
        }

        var showSearchResult by rememberSaveable {
            mutableStateOf(false)
        }
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
                showFilter = openSearchHistory || showSearchResult,
                onTextChange = {
                    viewModel.updateSearchTextState(it)
                },
                onClearText = {
                    openSearchHistory = true
                },
                onSearchClicked = {
                    openSearchHistory = false
                    showSearchResult = true
                    viewModel.searchAnimeByName(name = it)
                    isSearchHistoryItemClick = false
                },
                clickOnSearchView = {
                    openSearchHistory = true
                    showSearchResult = false
                    showStartAnimation = false
                },
                onCloseSearchView = {
                    openSearchHistory = false
                    showSearchResult = false
                    showStartAnimation = true
                },
                onFilterClicked = { keyboardController ->
                    keyboardController?.hide()
                    onFilterClicked()
                }

        )

        if (showStartAnimation) {
            SideEffect {
                viewModel.clearAllFilters()
            }
            //TODO()
        }

        if (openSearchHistory) {
            Column {
                Filters(filterList, filterListState)
                SearchHistory(
                        searchHistory = searchHistory,
                        onSearchItemClick = {
                            viewModel.updateSearchTextState(it)
                            isSearchHistoryItemClick = true
                        }
                )
            }
        }

        if (showSearchResult) {
            Column {
                Filters(filterList, filterListState)
                when (val results = searchState) {

                    is SearchAnimeScreenState.SearchResult -> {
                        animeList = results.result
                        SearchResult(
                                searchResults = animeList,
                                onAnimeItemClick = onAnimeItemClick
                        )
                    }

                    is SearchAnimeScreenState.SearchFailure -> {
                        LostInternetAnimation()
                        Log.d("tag", "failure")
                    }

                    is SearchAnimeScreenState.Loading -> {
                        Log.d("tag", "load")
                        ShimmerList()
                    }

                    is SearchAnimeScreenState.Initial -> {}

                }
            }
        }
    }
}

@Composable
private fun LostInternetAnimation(){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lost_internet))

    LottieAnimation(composition = composition, iterations = 4)

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

    OutlinedButton(
            onClick = { },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
            ),
            border = BorderStroke(2.dp, color = MaterialTheme.colors.primary),
    ) {
        Text(
                text = text,
                color = MaterialTheme.colors.background,
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
        searchResults: List<AnimeInfo>,
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
        items(items = searchResults, key = { it.id }) {
            AnimeCard(
                    item = it,
                    onAnimeItemClick = onAnimeItemClick
            )
        }
    }
}










