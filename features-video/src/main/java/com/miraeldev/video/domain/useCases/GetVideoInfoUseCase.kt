package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetVideoInfoUseCase(private val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.getVideoInfo()
}