package com.miraelDev.hikari.domain.repository

import androidx.paging.PagingData
import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    fun getNewAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPopularAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getNameAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getFilmsAnimeList(): Flow<PagingData<AnimeInfo>>

}