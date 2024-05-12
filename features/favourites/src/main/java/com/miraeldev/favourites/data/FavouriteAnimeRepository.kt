package com.miraeldev.favourites.data

import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.result.ResultAnimeInfo
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeRepository {

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo)

    fun getFavouriteAnimeList(): Flow<ResultAnimeInfo>

    suspend fun loadAnimeList()

    suspend fun searchAnimeItemInDatabase(name: String)

    suspend fun searchAnimeByName(name: String)

    fun saveSearchText(searchText: String)
}
