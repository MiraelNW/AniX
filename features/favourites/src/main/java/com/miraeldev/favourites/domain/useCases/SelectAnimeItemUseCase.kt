package com.miraeldev.favourites.domain.useCases

import com.miraeldev.favourites.data.FavouriteAnimeRepository
import com.miraeldev.models.anime.AnimeInfo
import me.tatarka.inject.annotations.Inject

@Inject
class SelectAnimeItemUseCase(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: AnimeInfo) =
        repository.selectAnimeItem(isSelected, animeInfo)
}