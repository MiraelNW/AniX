package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetFavouriteAnimeListUseCase(private val repository: FavouriteAnimeRepository) {

    operator fun invoke() = repository.getFavouriteAnimeList()
}