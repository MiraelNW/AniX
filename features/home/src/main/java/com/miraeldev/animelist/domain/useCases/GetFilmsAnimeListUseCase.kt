package com.miraeldev.animelist.domain.useCases

import com.miraeldev.animelist.data.AnimeListRepository
import javax.inject.Inject

class GetFilmsAnimeListUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke() = repository.getFilmsAnimeList()
}