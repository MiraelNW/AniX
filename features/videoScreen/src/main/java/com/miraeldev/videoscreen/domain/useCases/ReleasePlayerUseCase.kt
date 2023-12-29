package com.miraeldev.videoscreen.domain.useCases

import com.miraeldev.videoscreen.data.repository.VideoPlayerRepository
import javax.inject.Inject

class ReleasePlayerUseCase @Inject constructor(private val repository: VideoPlayerRepository) {
    suspend operator fun invoke() = repository.releasePlayer()
}