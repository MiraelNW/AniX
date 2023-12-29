package com.miraeldev.videoscreen.data.repository

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.video.PlayerWrapper
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerRepository {

    fun getVideoPlayer(): StateFlow<PlayerWrapper>

    fun loadVideoPlayer(animeInfo: AnimeInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    suspend fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)

}