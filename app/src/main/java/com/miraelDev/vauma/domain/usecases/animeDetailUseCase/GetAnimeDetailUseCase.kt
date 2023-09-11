package com.miraelDev.vauma.domain.usecases.animeDetailUseCase

import com.miraelDev.vauma.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke() = repository.getAnimeDetail()
}