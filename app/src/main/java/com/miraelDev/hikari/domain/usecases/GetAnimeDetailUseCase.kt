package com.miraelDev.hikari.domain.usecases

import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke(id:Int) = repository.getAnimeDetail(id)
}