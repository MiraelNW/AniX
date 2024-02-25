package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadAnimeListUseCase(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke() = repository.loadAnimeList()

}