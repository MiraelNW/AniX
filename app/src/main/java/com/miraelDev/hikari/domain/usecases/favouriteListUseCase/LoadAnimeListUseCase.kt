package com.miraelDev.hikari.domain.usecases.favouriteListUseCase

import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class LoadAnimeListUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke() = repository.loadAnimeList()

}