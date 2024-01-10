package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import javax.inject.Inject

class SelectAnimeItemUseCase @Inject constructor(private val repository: AnimeDetailRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: AnimeDetailInfo) =
        repository.selectAnimeItem(isSelected, animeInfo)

}