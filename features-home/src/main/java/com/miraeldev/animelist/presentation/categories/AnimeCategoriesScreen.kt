package com.miraeldev.animelist.presentation.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.miraeldev.animelist.presentation.categories.categoriesComponent.CategoriesComponent
import com.miraeldev.api.VaumaImageLoader

@Composable
fun AnimeCategoriesScreen(
    component: CategoriesComponent,
    categoryId: Int,
    imageLoader: VaumaImageLoader
) {
    Column(
        Modifier
            .systemBarsPadding()
            .systemGestureExclusion()
    ) {

        ScrollableTabWithViewPager(
            component = component,
            imageLoader = imageLoader,
            onAnimeItemClick = component::onAnimeItemClick,
            categoryId = categoryId
        )
    }
}
