package com.miraeldev.videoscreen.domain.useCases

import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadSpecificEpisodeUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    operator fun invoke(videoId:Int) = repository.loadSpecificEpisode(videoId)
}