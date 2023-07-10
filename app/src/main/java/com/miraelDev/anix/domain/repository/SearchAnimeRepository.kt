package com.miraelDev.anix.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SearchAnimeRepository {

    fun getFilterList(): StateFlow<List<String>>

    suspend fun addToFilterList(categoryId:Int,category:String)

    suspend fun removeFromFilterList(categoryId:Int,category:String)

    suspend fun clearAllFilters()

}