@file:OptIn(ExperimentalFoundationApi::class)

package com.miraelDev.vauma.presentation.animeListScreen.animeList

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.miraelDev.vauma.domain.models.AnimeInfo

@Composable
fun HomeScreen(
    onThemeButtonClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAnimeItemClick: (Int) -> Unit,
    viewModel: AnimeListViewModel = hiltViewModel()
) {
    Box {
        val newCategoryList =
            viewModel.newAnimeList.collectAsLazyPagingItems()

        val filmsAnimeList =
            viewModel.filmsAnimeList.collectAsLazyPagingItems()

        val popularAnimeList =
            viewModel.popularAnimeList.collectAsLazyPagingItems()

        val nameAnimeList =
            viewModel.nameAnimeList.collectAsLazyPagingItems()

        AnimeList(
            newCategoryList = newCategoryList,
            filmsAnimeList = filmsAnimeList,
            popularAnimeList = popularAnimeList,
            nameAnimeList = nameAnimeList,
            onSettingsClick = onSettingsClick,
            onThemeButtonClick = onThemeButtonClick,
            onAnimeItemClick = onAnimeItemClick
        )
    }
}

@Composable
fun AnimeList(
    newCategoryList: LazyPagingItems<AnimeInfo>,
    filmsAnimeList: LazyPagingItems<AnimeInfo>,
    popularAnimeList: LazyPagingItems<AnimeInfo>,
    nameAnimeList: LazyPagingItems<AnimeInfo>,
    onSettingsClick: () -> Unit,
    onThemeButtonClick: () -> Unit,
    onAnimeItemClick: (Int) -> Unit
) {
    Column(
        Modifier
            .systemGestureExclusion()
    ) {

        var darkTheme by rememberSaveable { mutableStateOf(false) }

        Toolbar(
            onSettingsClick = onSettingsClick,
            onThemeButtonClick = {
                darkTheme = !darkTheme
                onThemeButtonClick()
            },
            darkTheme = darkTheme
        )

        ScrollableTabWithViewPager(
            newCategoryList = newCategoryList,
            filmsAnimeList = filmsAnimeList,
            popularAnimeList = popularAnimeList,
            nameAnimeList = nameAnimeList,
            onAnimeItemClick = onAnimeItemClick
        )
    }
}



