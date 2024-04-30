package com.miraeldev.animelist.domain.useCases

import com.miraeldev.animelist.data.HomeRepository
import com.miraeldev.models.anime.LastWatchedAnime
import me.tatarka.inject.annotations.Inject

@Inject
class LoadVideoIdUseCase(private val homeRepository: HomeRepository) {

    operator fun invoke(animeInfo: LastWatchedAnime) = homeRepository.loadVideoId(animeInfo)
}