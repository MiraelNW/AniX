package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadSpecificEpisodeUseCase(private val repository: VideoPlayerRepository) {
    operator fun invoke(videoId: Int) = repository.loadSpecificEpisode(videoId)
}