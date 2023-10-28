package com.miraeldev.videoscreen.data.repository

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.videoscreen.domain.models.PlayerWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerRepository {

    fun getVideoPlayer(): Flow<PlayerWrapper>

    fun loadVideoId(id :Int)

    fun loadVideoPlayer(animeInfo: AnimeInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)

}