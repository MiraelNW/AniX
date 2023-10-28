package com.miraeldev.videoscreen.domain.useCases

import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadVideoIdUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke(videoId:Int) = repository.loadVideoId(videoId)
}