@file:OptIn(ExperimentalFoundationApi::class)

package com.miraelDev.vauma.presentation.animeListScreen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.miraelDev.vauma.R
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.ui.theme.Montserrat

@Composable
fun HomeScreen(
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
            onAnimeItemClick = onAnimeItemClick,
        )
    }
}

@Composable
fun AnimeList(
    newCategoryList: LazyPagingItems<AnimeInfo>,
    filmsAnimeList: LazyPagingItems<AnimeInfo>,
    popularAnimeList: LazyPagingItems<AnimeInfo>,
    nameAnimeList: LazyPagingItems<AnimeInfo>,
    onAnimeItemClick: (Int) -> Unit,
) {
    Column(
        Modifier.systemGestureExclusion()
    ) {

        Toolbar(
            text = stringResource(R.string.short_app_name),
            color = MaterialTheme.colors.primary,
            spacerWidth = 2.dp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium
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



