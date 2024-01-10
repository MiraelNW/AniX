package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadSpecificEpisodeUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    operator fun invoke(videoId:Int) = repository.loadSpecificEpisode(videoId)
}