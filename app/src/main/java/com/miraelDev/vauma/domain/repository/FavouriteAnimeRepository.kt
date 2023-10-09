package com.miraelDev.vauma.domain.repository

import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeRepository {

    suspend fun selectAnimeItem(isSelected: Boolean,animeInfo: AnimeInfo)

    fun getFavouriteAnimeList(): Flow<Result<AnimeInfo>>

    suspend fun loadAnimeList()

    suspend fun searchAnimeItemInDatabase(name:String)

}