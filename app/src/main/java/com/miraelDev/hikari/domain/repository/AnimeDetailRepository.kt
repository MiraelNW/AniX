package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AnimeDetailRepository {

    fun getAnimeDetail(): Flow<Result>
    suspend fun loadAnimeDetail(animeId: Int)

    suspend fun selectAnimeItem(isSelected: Boolean, animeInfo: AnimeInfo)

}