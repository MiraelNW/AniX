package com.miraelDev.vauma.domain.usecases.favouriteListUseCase

import com.miraelDev.vauma.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class LoadAnimeListUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke() = repository.loadAnimeList()

}