package com.miraelDev.anix.domain.repository

import com.miraelDev.anix.domain.models.AnimeInfo
import kotlinx.coroutines.flow.SharedFlow

interface AnimeListRepository {

    fun getAnimeListByCategory(category: Int)

    fun getAnimeList(): SharedFlow<List<AnimeInfo>>

}