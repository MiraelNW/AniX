package com.miraelDev.hikari.presentation


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
import androidx.media3.extractor.BinarySearchSeeker.TimestampSearchResult
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard.AnimeCard
import com.miraelDev.hikari.presentation.SearchAimeScreen.AnimeCard.LastSearchedAnime
import com.miraelDev.hikari.presentation.AnimeListScreen.AnimeSearchView
import com.miraelDev.hikari.presentation.SearchAimeScreen.SearchAnimeViewModel
import com.miraelDev.hikari.presentation.shimmerList.ShimmerList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAnimeScreen(
    onFilterClicked: () -> Unit,
    onAnimeItemClick: (Int) -> Unit
) {


    val viewModel = hiltViewModel<SearchAnimeViewModel>()

    val searchTextState by viewModel.searchTextState
    val filterList by viewModel.filterList.collectAsState(listOf())
    val searchResults by viewModel.animeBySearch.collectAsState()

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

        val filterListState = rememberLazyListState()

        AnimeSearchView(
            text = searchTextState,
            showFilter = openSearchHistory || showSearchResult,
            onTextChange = {
                viewModel.updateSearchTextState(it)
            },
            onClearText = {
                openSearchHistory = true
            },
            onSearchClicked = { animeName ->
                openSearchHistory = false
                showSearchResult = true
                viewModel.searchAnimeByName(animeName)
//                TODO("viewModel function to load")
//                TODO("viewModel function to save in database search")
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
                SearchHistory()
            }
        }

        if (showSearchResult) {
            Column {
                Filters(filterList, filterListState)
                SearchResult(
                    searchResults = searchResults,
                    onAnimeItemClick = onAnimeItemClick
                )
            }
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
private fun SearchHistory() {
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
        repeat(20) {
            item {
                LastSearchedAnime(AnimeInfo(1))
            }
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










