package com.miraelDev.hikari.data.Repository

import com.miraelDev.hikari.data.mapper.Mapper
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AnimeDetailRepositoryImpl @Inject constructor(
    mapper: Mapper
) : AnimeDetailRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _animeDetail = MutableStateFlow<AnimeInfo>(AnimeInfo(0))
    private val _videoId = MutableStateFlow(0)

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

    override fun getAnimeDetail(): StateFlow<AnimeInfo> = _animeDetail.asStateFlow()

    override fun loadAnimeDetail(animeId: Int) {
        _animeDetail.value = animeList.find { it.id == animeId } ?: AnimeInfo(0)
    }

    override fun loadVideoId(videoId: Int) {
        _videoId.value = videoId
    }

    override fun getVideoId(): StateFlow<Int> = _videoId.asStateFlow()


}