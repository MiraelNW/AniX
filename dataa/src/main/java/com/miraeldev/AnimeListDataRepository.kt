package com.miraeldev

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import kotlinx.coroutines.flow.Flow

interface AnimeListDataRepository {

    fun getPagingNewAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPagingPopularAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPagingNameAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPagingFilmsAnimeList(): Flow<PagingData<AnimeInfo>>

}