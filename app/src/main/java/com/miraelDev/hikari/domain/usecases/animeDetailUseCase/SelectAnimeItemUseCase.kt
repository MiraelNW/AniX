package com.miraelDev.hikari.domain.usecases.animeDetailUseCase

import com.miraelDev.hikari.data.Repository.VideoPlayerRepositoryImpl
import com.miraelDev.hikari.domain.models.AnimeInfo
import com.miraelDev.hikari.domain.repository.AnimeDetailRepository
import javax.inject.Inject

class SelectAnimeItemUseCase @Inject constructor(private val repository: AnimeDetailRepository) {

    suspend operator fun invoke(isSelected : Boolean,animeInfo:AnimeInfo) = repository.selectAnimeItem(isSelected,animeInfo)

}