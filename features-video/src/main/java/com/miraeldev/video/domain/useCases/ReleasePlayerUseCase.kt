package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import javax.inject.Inject

class ReleasePlayerUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    suspend operator fun invoke() = repository.releasePlayer()
}