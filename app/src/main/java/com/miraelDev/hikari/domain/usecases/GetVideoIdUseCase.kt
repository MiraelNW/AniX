package com.miraelDev.hikari.domain.usecases

import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class GetVideoIdUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke() = repository.getVideoId()
}