package com.miraelDev.hikari.domain.repository

import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.models.PlayerWrapper
import com.miraelDev.hikari.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerRepository {

    fun getVideoPlayer(): StateFlow<PlayerWrapper>

    fun loadVideoId(id :Int)

    fun loadVideoPlayer(animeInfo: AnimeInfo)

    fun loadNextEpisode()

    fun loadPrevEpisode()

    fun releasePlayer()

    fun loadSpecificEpisode(specificEpisode: Int)

}