package com.miraelDev.vauma.domain.repository

import androidx.paging.PagingData
import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface FavouriteAnimeRepository {

    suspend fun selectAnimeItem(isSelected: Boolean,animeInfo: AnimeInfo)

    fun getFavouriteAnimeList(): Flow<Result<AnimeInfo>>

    suspend fun loadAnimeList()

    suspend fun searchAnimeItemInDatabase(name:String)

}