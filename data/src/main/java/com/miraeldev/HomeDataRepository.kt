package com.miraeldev

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface HomeDataRepository {

    suspend fun loadData()

    fun getNewAnimeList(): Flow<List<AnimeInfo>>

    fun getPopularAnimeList(): Flow<List<AnimeInfo>>

    fun getNameAnimeList(): Flow<List<AnimeInfo>>

    fun getFilmsAnimeList(): Flow<List<AnimeInfo>>

    fun getUserInfo():Flow<User>

}