package com.miraeldev.api

import com.miraeldev.models.paging.PagingState
import kotlinx.coroutines.flow.Flow

interface AnimeListDataRepository {

    fun getPagingNewAnimeList(): Flow<PagingState>

    fun getPagingPopularAnimeList(): Flow<PagingState>

    fun getPagingNameAnimeList(): Flow<PagingState>

    fun getPagingFilmsAnimeList(): Flow<PagingState>

    suspend fun loadNewCategoryNextPage()

    suspend fun loadPopularCategoryNextPage()

    suspend fun loadNameCategoryNextPage()

    suspend fun loadFilmCategoryNextPage()
}