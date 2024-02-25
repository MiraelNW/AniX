package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SearchAnimeItemInDatabaseUseCase(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(name:String) = repository.searchAnimeItemInDatabase(name)

}