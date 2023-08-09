@file:OptIn(ExperimentalFoundationApi::class)

package com.miraelDev.hikari.presentation.AnimeListScreen.AnimeList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onThemeButtonClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAnimeItemClick: (Int) -> Unit
) {
    Box {

        val viewModel = hiltViewModel<AnimeListViewModel>()

        val screenStateNewAnimeList =
            viewModel.screenStateNewAnimeList.collectAsState(initial = AnimeListScreenState.Loading)

        val screenStateFilmsAnimeList =
            viewModel.screenStateFilmsAnimeList.collectAsState(initial = AnimeListScreenState.Loading)

        val screenStatePopularAnimeList =
            viewModel.screenStatePopularAnimeList.collectAsState(initial = AnimeListScreenState.Loading)

        val screenStateNameAnimeList =
            viewModel.screenStateNameAnimeList.collectAsState(initial = AnimeListScreenState.Loading)

        AnimeList(
            screenStateNewAnimeList = screenStateNewAnimeList,
            screenStateFilmsAnimeList = screenStateFilmsAnimeList,
            screenStatePopularAnimeList = screenStatePopularAnimeList,
            screenStateNameAnimeList = screenStateNameAnimeList,
            onSettingsClick = onSettingsClick,
            onThemeButtonClick = onThemeButtonClick,
            viewModel = viewModel,
            onAnimeItemClick = onAnimeItemClick
        )
    }
}

@Composable
fun AnimeList(
    screenStateNewAnimeList: State<AnimeListScreenState>,
    screenStateFilmsAnimeList: State<AnimeListScreenState>,
    screenStatePopularAnimeList: State<AnimeListScreenState>,
    screenStateNameAnimeList: State<AnimeListScreenState>,
    onSettingsClick: () -> Unit,
    onThemeButtonClick: () -> Unit,
    viewModel: AnimeListViewModel,
    onAnimeItemClick: (Int) -> Unit
) {
    Column(
        Modifier
            .systemGestureExclusion()
            .padding(bottom = 48.dp)
    ) {

        var darkTheme by remember { mutableStateOf(false) }

        Toolbar(
            onSettingsClick = onSettingsClick,
            onThemeButtonClick = {
                darkTheme = !darkTheme
                onThemeButtonClick()
            },
            darkTheme = darkTheme
        )

        ScrollableTabWithViewPager(
            screenStateNewAnimeList = screenStateNewAnimeList,
            screenStateFilmsAnimeList = screenStateFilmsAnimeList,
            screenStatePopularAnimeList = screenStatePopularAnimeList,
            screenStateNameAnimeList = screenStateNameAnimeList,
            viewModel = viewModel,
            onAnimeItemClick = onAnimeItemClick
        )
    }
}



