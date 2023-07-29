package com.miraelDev.hikari.domain.usecases

import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class LoadVideoIdUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke(videoId:Int) = repository.loadVideoId(videoId)
}