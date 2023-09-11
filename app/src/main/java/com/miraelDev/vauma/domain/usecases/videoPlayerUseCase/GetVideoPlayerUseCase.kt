package com.miraelDev.vauma.domain.usecases.videoPlayerUseCase

import com.miraelDev.vauma.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class GetVideoPlayerUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.getVideoPlayer()
}