package com.miraelDev.hikari.domain.usecases.videoPlayerUseCase

import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadVideoPlayerUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke(animeInfo: AnimeInfo) = repository.loadVideoPlayer(animeInfo)
}