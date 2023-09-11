package com.miraelDev.vauma.domain.usecases.homeUseCase

import com.miraelDev.vauma.domain.repository.AnimeListRepository
import javax.inject.Inject

class GetNameAnimeListUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke() = repository.getNameAnimeList()
}