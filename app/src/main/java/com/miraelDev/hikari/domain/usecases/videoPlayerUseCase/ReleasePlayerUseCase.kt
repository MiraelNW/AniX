package com.miraelDev.hikari.domain.usecases.videoPlayerUseCase

import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.VideoPlayerRepository
import javax.inject.Inject

class ReleasePlayerUseCase @Inject constructor(val repository: VideoPlayerRepository) {
    operator fun invoke() = repository.releasePlayer()
}