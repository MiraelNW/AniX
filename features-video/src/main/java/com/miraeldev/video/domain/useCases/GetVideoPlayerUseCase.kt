package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import javax.inject.Inject

class GetVideoPlayerUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.getVideoPlayer()
}