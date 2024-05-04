package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadVideoIdUseCase(val repository: AnimeDetailRepository) {
    operator fun invoke(animeItem: AnimeDetailInfo, id: Int) = repository.loadVideoId(animeItem, id)
}