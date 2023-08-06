package com.miraelDev.hikari.domain.usecases.homeUseCase

import com.miraelDev.hikari.domain.repository.AnimeListRepository
import javax.inject.Inject

class GetNewAnimeListUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke() = repository.getNewAnimeList()
}