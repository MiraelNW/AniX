package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AnimeDetailRepositoryImpl @Inject constructor(
    mapper: Mapper
) : AnimeDetailRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val animeList = listOf(
        AnimeInfo(1),
        AnimeInfo(2),
        AnimeInfo(3),
        AnimeInfo(4),
        AnimeInfo(5),
        AnimeInfo(6),
        AnimeInfo(7),
        AnimeInfo(8),
        AnimeInfo(9),
        AnimeInfo(10),
        AnimeInfo(11),
        AnimeInfo(12),
        AnimeInfo(13),
    )
    override fun getAnimeDetail(id: Int): AnimeInfo {
        return animeList.find { it.id == id } ?: AnimeInfo(0)
    }


}