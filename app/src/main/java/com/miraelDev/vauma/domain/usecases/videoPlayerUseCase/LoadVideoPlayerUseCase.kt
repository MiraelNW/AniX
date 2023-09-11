package com.miraelDev.vauma.domain.usecases.videoPlayerUseCase

import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadVideoPlayerUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke(animeInfo: AnimeInfo) = repository.loadVideoPlayer(animeInfo)
}