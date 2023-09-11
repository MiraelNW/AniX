package com.miraelDev.vauma.domain.usecases.animeDetailUseCase

import com.miraelDev.vauma.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class LoadAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    suspend operator fun invoke(id:Int) = repository.loadAnimeDetail(id)
}