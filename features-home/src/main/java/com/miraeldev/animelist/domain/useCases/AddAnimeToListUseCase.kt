package com.miraeldev.animelist.domain.useCases

import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class AddAnimeToListUseCase(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: LastWatchedAnime) =
        homeRepository.addAnimeToList(isSelected, animeInfo)

}