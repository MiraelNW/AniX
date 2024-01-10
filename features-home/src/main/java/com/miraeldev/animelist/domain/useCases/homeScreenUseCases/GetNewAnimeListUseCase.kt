package com.miraeldev.animelist.domain.useCases.homeScreenUseCases

import com.miraeldev.animelist.data.HomeRepository
import javax.inject.Inject

class GetNewAnimeListUseCase @Inject constructor(private val repository: HomeRepository) {
    operator fun invoke() = repository.getNewAnimeList()
}