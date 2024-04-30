package com.miraelDev.vauma.glue.video.repository

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.api.VideoPlayerDataRepository
import com.miraeldev.video.PlayerWrapper
import com.miraeldev.video.data.repository.VideoPlayerRepository
import kotlinx.coroutines.flow.StateFlow
import me.tatarka.inject.annotations.Inject

@Inject
class VideoPlayerRepositoryImpl(
    private val videoPlayerDataRepository: VideoPlayerDataRepository
) : VideoPlayerRepository {
    override fun getVideoInfo(): StateFlow<PlayerWrapper> {
        return videoPlayerDataRepository.getVideoInfo()
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