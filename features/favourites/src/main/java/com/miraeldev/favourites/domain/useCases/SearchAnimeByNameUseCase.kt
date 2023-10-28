package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import javax.inject.Inject

class SearchAnimeByNameUseCase @Inject constructor(val repository: FavouriteAnimeRepository) {
    suspend operator fun invoke(name: String) =
        repository.searchAnimeByName(name)
}