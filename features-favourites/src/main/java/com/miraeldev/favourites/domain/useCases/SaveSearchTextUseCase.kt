package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SaveSearchTextUseCase(private val repository: FavouriteAnimeRepository) {

    operator fun invoke(searchText:String) = repository.saveSearchText(searchText)

}