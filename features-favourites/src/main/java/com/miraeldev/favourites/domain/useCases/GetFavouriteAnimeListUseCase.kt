package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import javax.inject.Inject

class GetFavouriteAnimeListUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    operator fun invoke() = repository.getFavouriteAnimeList()

}