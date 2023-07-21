package com.miraelDev.hikari.domain.usecases.homeUseCase

import com.miraelDev.hikari.domain.repository.AnimeListRepository
import javax.inject.Inject

class GetAnimeListByCategoryUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke(category : Int) = repository.getAnimeListByCategory(category)
}