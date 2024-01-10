package com.miraeldev.animelist.domain.useCases

import com.miraeldev.animelist.data.HomeRepository
import com.miraeldev.anime.LastWatchedAnime
import javax.inject.Inject

class LoadVideoIdUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    operator fun invoke(animeInfo: LastWatchedAnime) = homeRepository.loadVideoId(animeInfo)

}