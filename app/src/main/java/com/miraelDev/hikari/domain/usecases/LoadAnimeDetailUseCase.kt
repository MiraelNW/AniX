package com.miraelDev.hikari.domain.usecases

import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class LoadAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    suspend operator fun invoke(id:Int) = repository.loadAnimeDetail(id)
}