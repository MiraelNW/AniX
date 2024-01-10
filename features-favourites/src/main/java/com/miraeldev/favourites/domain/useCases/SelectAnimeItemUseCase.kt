package com.miraeldev.favourites.domain.useCases

import com.miraeldev.anime.AnimeInfo
import com.miraeldev.favourites.data.FavouriteAnimeRepository
import javax.inject.Inject

class SelectAnimeItemUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: AnimeInfo)
        = repository.selectAnimeItem(isSelected, animeInfo)

}