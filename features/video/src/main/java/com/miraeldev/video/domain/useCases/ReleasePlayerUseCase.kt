package com.miraeldev.video.domain.useCases

import com.miraeldev.video.data.repository.VideoPlayerRepository
import me.tatarka.inject.annotations.Inject

@Inject
class ReleasePlayerUseCase constructor(private val repository: VideoPlayerRepository) {
    suspend operator fun invoke() = repository.releasePlayer()
}