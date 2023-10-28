package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import javax.inject.Inject

class LoadAnimeListUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke() = repository.loadAnimeList()

}