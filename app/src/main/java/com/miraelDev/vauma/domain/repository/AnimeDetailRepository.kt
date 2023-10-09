package com.miraelDev.vauma.domain.repository

import com.miraelDev.vauma.domain.models.anime.AnimeDetailInfo
import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface AnimeDetailRepository {

    fun getAnimeDetail(): Flow<Result<AnimeDetailInfo>>
    suspend fun loadAnimeDetail(animeId: Int)

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo)

}