package com.miraeldev.videoscreen.domain.useCases

import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadPreviousEpisodeUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.loadPrevEpisode()
}