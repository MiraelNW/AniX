package com.miraeldev.video.data.repository

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.video.PlayerWrapper
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerRepository {

    fun getVideoInfo(): StateFlow<PlayerWrapper>

    fun loadVideoPlayer(animeInfo: AnimeDetailInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    suspend fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)
}