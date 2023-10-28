package com.miraelDev.vauma.glue.videoScreen.repository

import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import com.miraeldev.videoscreen.domain.models.PlayerWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoPlayerRepositoryImpl @Inject constructor(
    private val videoPlayerDataRepository: VideoPlayerDataRepository
) : VideoPlayerRepository {
    override fun getVideoPlayer(): Flow<PlayerWrapper> {
        return videoPlayerDataRepository.getVideoPlayer().map {
            PlayerWrapper(
                it.exoPlayer,
                it.title,
                it.isFirstEpisode,
                it.isLastEpisode
            )
        }
    }

    override fun loadVideoId(id: Int) {
        videoPlayerDataRepository.loadVideoId(id)
    }

    override fun loadVideoPlayer(animeInfo: AnimeInfo) {
        videoPlayerDataRepository.loadVideoPlayer(animeInfo)
    }

    override fun loadNextEpisode() {
        videoPlayerDataRepository.loadNextEpisode()
    }

    override fun loadPrevEpisode() {
        videoPlayerDataRepository.loadPrevEpisode()
    }

    override fun releasePlayer() {
        videoPlayerDataRepository.releasePlayer()
    }

    override fun loadSpecificEpisode(specificEpisode: Int) {
        videoPlayerDataRepository.loadSpecificEpisode(specificEpisode)
    }

}