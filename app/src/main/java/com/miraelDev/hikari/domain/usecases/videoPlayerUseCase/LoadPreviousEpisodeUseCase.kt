package com.miraelDev.hikari.domain.usecases.videoPlayerUseCase

import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class LoadPreviousEpisodeUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.loadPrevEpisode()
}