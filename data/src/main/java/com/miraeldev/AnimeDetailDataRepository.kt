package com.miraeldev

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.result.ResultAnimeDetail
import kotlinx.coroutines.flow.Flow

interface AnimeDetailDataRepository {

    fun getAnimeDetail(): Flow<ResultAnimeDetail>

    suspend fun loadAnimeDetail(animeId: Int)

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo)

}