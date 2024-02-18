package com.miraeldev.search.presentation.filterScreen.filterComponent

import kotlinx.coroutines.flow.StateFlow

interface FilterComponent {


    val model:StateFlow<FilterStore.State>

    fun selectCategory(categoryId: Int, category: String, isSelected: Boolean)
    fun clearAllFilter()
    fun onBackPressed()

}