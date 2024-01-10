package com.miraelDev.vauma.glue.video.repository

import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.video.PlayerWrapper
import com.miraeldev.video.data.repository.VideoPlayerRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class VideoPlayerRepositoryImpl @Inject constructor(
    private val videoPlayerDataRepository: VideoPlayerDataRepository
) : VideoPlayerRepository {
    override fun getVideoPlayer(): StateFlow<PlayerWrapper> {
        return videoPlayerDataRepository.getVideoPlayer()
    }

    override fun loadVideoPlayer(animeInfo: AnimeDetailInfo) {
        videoPlayerDataRepository.loadVideoPlayer(animeInfo)
    }

    override fun loadNextEpisode() {
        videoPlayerDataRepository.loadNextEpisode()
    }

    override fun loadPrevEpisode() {
        videoPlayerDataRepository.loadPrevEpisode()
    }

    override suspend fun releasePlayer() {
        videoPlayerDataRepository.releasePlayer()
    }

    override fun loadSpecificEpisode(specificEpisode: Int) {
        videoPlayerDataRepository.loadSpecificEpisode(specificEpisode)
    }

}