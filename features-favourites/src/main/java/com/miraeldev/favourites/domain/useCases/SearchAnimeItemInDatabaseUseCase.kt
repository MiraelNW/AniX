package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import javax.inject.Inject

class SearchAnimeItemInDatabaseUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(name:String) = repository.searchAnimeItemInDatabase(name)

}