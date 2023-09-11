package com.miraelDev.vauma.domain.usecases.favouriteListUseCase

import com.miraelDev.vauma.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class GetFavouriteAnimeListUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    operator fun invoke() = repository.getFavouriteAnimeList()

}