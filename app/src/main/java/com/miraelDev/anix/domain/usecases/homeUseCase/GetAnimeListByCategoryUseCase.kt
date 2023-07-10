package com.miraelDev.anix.domain.usecases.homeUseCase

import com.miraelDev.anix.domain.repository.AnimeListRepository
import javax.inject.Inject

class GetAnimeListByCategoryUseCase @Inject constructor(private val repository: AnimeListRepository) {
    operator fun invoke(category : Int) = repository.getAnimeListByCategory(category)
}