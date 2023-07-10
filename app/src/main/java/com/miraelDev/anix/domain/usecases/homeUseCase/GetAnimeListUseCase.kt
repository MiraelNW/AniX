package com.miraelDev.anix.domain.usecases.homeUseCase

import com.miraelDev.anix.domain.repository.AnimeListRepository
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke() = repository.getAnimeList()
}