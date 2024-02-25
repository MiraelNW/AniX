package com.miraeldev.animelist.domain.useCases

import com.miraeldev.anime.LastWatchedAnime
import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadVideoIdUseCase(private val homeRepository: HomeRepository) {

    operator fun invoke(animeInfo: LastWatchedAnime) = homeRepository.loadVideoId(animeInfo)

}