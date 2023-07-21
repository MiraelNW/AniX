package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import kotlinx.coroutines.flow.SharedFlow

interface AnimeListRepository {

    fun getAnimeListByCategory(category: Int)

    fun getAnimeList(): SharedFlow<List<AnimeInfo>>

}