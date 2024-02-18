package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import javax.inject.Inject

class GetVideoInfoUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.getVideoInfo()
}