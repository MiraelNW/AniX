package com.miraelDev.hikari.domain.usecases.favouriteListUseCase

import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class SearchAnimeItemInDatabaseUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(name:String) = repository.searchAnimeItemInDatabase(name)

}