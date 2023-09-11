package com.miraelDev.vauma.domain.usecases.favouriteListUseCase

import com.miraelDev.vauma.domain.models.AnimeInfo
import com.miraelDev.vauma.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class SelectAnimeItemUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: AnimeInfo)
        = repository.selectAnimeItem(isSelected, animeInfo)

}