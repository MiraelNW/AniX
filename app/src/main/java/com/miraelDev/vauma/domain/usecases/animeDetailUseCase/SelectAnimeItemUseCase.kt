package com.miraelDev.vauma.domain.usecases.animeDetailUseCase

import com.miraelDev.vauma.domain.models.anime.AnimeInfo
import com.miraelDev.vauma.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class SelectAnimeItemUseCase @Inject constructor(private val repository: AnimeDetailRepository) {

    suspend operator fun invoke(isSelected : Boolean,animeInfo: AnimeInfo) = repository.selectAnimeItem(isSelected,animeInfo)

}