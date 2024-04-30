package com.miraeldev.api

import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.result.ResultAnimeDetail
import kotlinx.coroutines.flow.Flow

interface AnimeDetailDataRepository {

    fun getAnimeDetail(): Flow<ResultAnimeDetail>

    suspend fun loadAnimeDetail(animeId: Int)

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo)
}