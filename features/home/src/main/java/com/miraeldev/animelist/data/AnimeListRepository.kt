package com.miraeldev.animelist.data

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    fun getNewAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPopularAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getNameAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getFilmsAnimeList(): Flow<PagingData<AnimeInfo>>

}