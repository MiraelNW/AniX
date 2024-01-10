package com.miraeldev.animelist.domain.useCases

import com.miraeldev.animelist.data.HomeRepository
import com.miraeldev.anime.LastWatchedAnime
import javax.inject.Inject

class AddAnimeToListUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: LastWatchedAnime) =
        homeRepository.addAnimeToList(isSelected, animeInfo)

}