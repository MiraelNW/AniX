package com.miraelDev.hikari.domain.usecases.favouriteListUseCase

import com.miraelDev.hikari.data.Repository.VideoPlayerRepositoryImpl
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.models.FavouriteAnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import com.miraelDev.hikari.domain.repository.FavouriteAnimeRepository
import javax.inject.Inject

class SelectAnimeItemUseCase @Inject constructor(private val repository: FavouriteAnimeRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: AnimeInfo)
        = repository.selectAnimeItem(isSelected, animeInfo)

}