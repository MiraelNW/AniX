package com.miraeldev.videoscreen.domain.useCases

import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadNextEpisodeUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.loadNextEpisode()
}