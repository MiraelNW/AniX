package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.anime.AnimeDetailInfo
import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SelectAnimeItemUseCase(private val repository: AnimeDetailRepository) {

    suspend operator fun invoke(isSelected: Boolean, animeInfo: AnimeDetailInfo) =
        repository.selectAnimeItem(isSelected, animeInfo)

}