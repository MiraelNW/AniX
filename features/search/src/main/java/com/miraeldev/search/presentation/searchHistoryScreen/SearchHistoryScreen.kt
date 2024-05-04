package com.miraeldev.search.presentation.searchHistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.miraeldev.search.presentation.animeCard.LastSearchedAnime
import com.miraeldev.search.presentation.searchHistoryScreen.searchHistoryComponent.SearchHistoryComponent
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SearchHistoryScreen(component: SearchHistoryComponent) {
    val model by component.model.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        AnimatedSearchView(
            text = { model.search },
            onTextChange = component::onSearchChange,
            onSearchClicked = component::searchAnimeByName,
            onCloseSearchView = component::showInitialList,
            onFilterClicked = component::onFilterClicked
        )

        Filters(filterList = model.filterList)
        SearchHistory(
            searchHistory = model.historyList,
            onSearchItemClick = component::onSearchHistoryItemClick
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
