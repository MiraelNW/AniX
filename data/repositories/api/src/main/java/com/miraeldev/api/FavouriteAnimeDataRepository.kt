package com.miraeldev.api

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.result.ResultAnimeInfo
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeDataRepository {

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo)

    fun getFavouriteAnimeList(): Flow<ResultAnimeInfo>

    suspend fun loadAnimeList()

    suspend fun searchAnimeItemInDatabase(name: String)

}