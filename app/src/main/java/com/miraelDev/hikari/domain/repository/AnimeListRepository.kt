package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface AnimeListRepository {

//    fun getAnimeListByCategory(category: Int)

    fun getNewAnimeList(): Flow<List<AnimeInfo>>

    fun getPopularAnimeList(): Flow<List<AnimeInfo>>

    fun getNameAnimeList(): Flow<List<AnimeInfo>>

    fun getFilmsAnimeList(): Flow<List<AnimeInfo>>

}