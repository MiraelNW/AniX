package com.miraelDev.vauma.glue.videoScreen.repository

import com.miraeldev.VideoPlayerDataRepository
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.video.PlayerWrapper
import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class VideoPlayerRepositoryImpl @Inject constructor(
    private val videoPlayerDataRepository: VideoPlayerDataRepository
) : VideoPlayerRepository {
    override fun getVideoPlayer(): StateFlow<PlayerWrapper> {
        return videoPlayerDataRepository.getVideoPlayer()
//            .map {
//            PlayerWrapper(
//                it.exoPlayer,
//                it.title,
//                it.isFirstEpisode,
//                it.isLastEpisode
//            )
//        }
    }

//    override fun loadVideoId(animeItem:AnimeDetailInfo, id: Int) {
//        videoPlayerDataRepository.loadVideoId(id)
//    }

    override fun loadVideoPlayer(animeInfo: AnimeInfo) {
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