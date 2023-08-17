package com.miraelDev.hikari.domain.usecases.animeDetailUseCase

import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke() = repository.getAnimeDetail()
}