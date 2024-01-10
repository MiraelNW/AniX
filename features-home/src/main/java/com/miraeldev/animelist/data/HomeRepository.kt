package com.miraeldev.animelist.data

import androidx.paging.PagingData
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.user.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getPagingNewAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPagingPopularAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPagingNameAnimeList(): Flow<PagingData<AnimeInfo>>

    fun getPagingFilmsAnimeList(): Flow<PagingData<AnimeInfo>>


    suspend fun loadData()

    fun getNewAnimeList(): Flow<List<AnimeInfo>>

    fun getPopularAnimeList(): Flow<List<AnimeInfo>>

    fun getNameAnimeList(): Flow<List<AnimeInfo>>

    fun getFilmsAnimeList(): Flow<List<AnimeInfo>>

    suspend fun addAnimeToList(isSelected: Boolean, animeInfo: LastWatchedAnime)

    fun getUserInfo():Flow<User>

    fun loadVideoId(animeItem: LastWatchedAnime)

}