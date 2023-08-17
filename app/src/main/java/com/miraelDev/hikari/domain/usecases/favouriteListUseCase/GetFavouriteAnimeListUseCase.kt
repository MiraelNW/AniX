package com.miraelDev.hikari.domain.usecases.favouriteListUseCase

import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class GetFavouriteAnimeListUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    operator fun invoke() = repository.getFavouriteAnimeList()

}