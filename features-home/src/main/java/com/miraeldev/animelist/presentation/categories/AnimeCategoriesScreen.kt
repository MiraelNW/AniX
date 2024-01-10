package com.miraeldev.animelist.presentation.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.miraeldev.anime.AnimeInfo

@Composable
fun AnimeCategoriesScreen(
    categoryId: Int,
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
            categoryId = categoryId,
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
    categoryId: Int,
    onAnimeItemClick: (Int) -> Unit,
) {
    Column(
        Modifier
            .systemBarsPadding()
            .systemGestureExclusion()
    ) {

        ScrollableTabWithViewPager(
            newCategoryList = newCategoryList,
            filmsAnimeList = filmsAnimeList,
            popularAnimeList = popularAnimeList,
            nameAnimeList = nameAnimeList,
            onAnimeItemClick = onAnimeItemClick,
            categoryId = categoryId
        )
    }
}



