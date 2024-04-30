package com.miraeldev.animelist.presentation.categories.categoriesComponent

import kotlinx.coroutines.flow.StateFlow

interface CategoriesComponent {

    val model: StateFlow<CategoriesStore.State>

    fun onAnimeItemClick(id: Int)

    fun loadNewCategoryNextPage()
    fun loadPopularCategoryNextPage()
    fun loadNameCategoryNextPage()
    fun loadFilmCategoryNextPage()
}