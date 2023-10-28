package com.miraeldev.animelist.domain.useCases

import com.miraeldev.animelist.data.AnimeListRepository
import javax.inject.Inject

class GetPopularAnimeListUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke() = repository.getPopularAnimeList()
}