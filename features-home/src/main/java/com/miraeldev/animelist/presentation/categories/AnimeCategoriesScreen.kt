package com.miraeldev.animelist.presentation.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesComponent

@Composable
fun AnimeCategoriesScreen(
    component: CategoriesComponent,
    categoryId: Int
) {
    val model by component.model.collectAsStateWithLifecycle()

    Box {
        val newCategoryList =
            model.newListState.collectAsLazyPagingItems()

        val filmsAnimeList =
            model.filmsListState.collectAsLazyPagingItems()

        val popularAnimeList =
            model.popularListState.collectAsLazyPagingItems()

        val nameAnimeList =
            model.nameListState.collectAsLazyPagingItems()

        AnimeList(
            newCategoryList = newCategoryList,
            filmsAnimeList = filmsAnimeList,
            popularAnimeList = popularAnimeList,
            nameAnimeList = nameAnimeList,
            categoryId = categoryId,
            onAnimeItemClick = component::onAnimeItemClick,
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



