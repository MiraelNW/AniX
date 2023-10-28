package com.miraeldev

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.domain.models.PlayerWrapper
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerDataRepository {

    fun getVideoPlayer(): StateFlow<PlayerWrapper>

    fun loadVideoId(id :Int)

    fun loadVideoPlayer(animeInfo: AnimeInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)

}