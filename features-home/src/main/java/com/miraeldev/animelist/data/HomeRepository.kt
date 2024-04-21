package com.miraeldev.animelist.data

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.models.paging.PagingState
import com.miraeldev.models.user.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getPagingNewAnimeList(): Flow<PagingState>

    fun getPagingPopularAnimeList(): Flow<PagingState>

    fun getPagingNameAnimeList(): Flow<PagingState>

    fun getPagingFilmsAnimeList(): Flow<PagingState>

    suspend fun loadNewCategoryNextPage()

    suspend fun loadPopularCategoryNextPage()

    suspend fun loadNameCategoryNextPage()

    suspend fun loadFilmCategoryNextPage()


    suspend fun loadData(): Map<Int, List<AnimeInfo>>

    suspend fun addAnimeToList(isSelected: Boolean, animeInfo: LastWatchedAnime)

    fun getUserInfo():Flow<User>

    fun loadVideoId(animeItem: LastWatchedAnime)

}