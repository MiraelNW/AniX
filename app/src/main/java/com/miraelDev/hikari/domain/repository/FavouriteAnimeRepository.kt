package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeRepository {

    suspend fun selectAnimeItem(isSelected: Boolean,animeInfo: AnimeInfo)

    fun getFavouriteAnimeList(): Flow<Result>

    suspend fun loadAnimeList()

    suspend fun searchAnimeItemInDatabase(name:String)

}