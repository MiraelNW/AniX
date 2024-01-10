package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import javax.inject.Inject

class LoadVideoIdUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke(animeItem: AnimeDetailInfo, id: Int) = repository.loadVideoId(animeItem, id)
}