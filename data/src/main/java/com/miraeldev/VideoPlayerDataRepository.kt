package com.miraeldev

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.video.PlayerWrapper
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerDataRepository {

    fun getVideoPlayer(): StateFlow<PlayerWrapper>

    fun loadVideoId(animeItem: AnimeDetailInfo, videoId :Int)

    fun loadVideoPlayer(animeInfo: AnimeDetailInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    suspend fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)

}