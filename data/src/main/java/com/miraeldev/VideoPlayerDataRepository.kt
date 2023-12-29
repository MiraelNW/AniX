package com.miraeldev

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.video.PlayerWrapper
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerDataRepository {

    fun getVideoPlayer(): StateFlow<PlayerWrapper>

    fun loadVideoId(animeItem: AnimeInfo, videoId :Int)

    fun loadVideoPlayer(animeInfo: AnimeInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    suspend fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)

}