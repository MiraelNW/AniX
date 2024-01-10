package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadPreviousEpisodeUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.loadPrevEpisode()
}