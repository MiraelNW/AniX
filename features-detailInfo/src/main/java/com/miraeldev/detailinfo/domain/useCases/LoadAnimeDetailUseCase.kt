package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import javax.inject.Inject

class LoadAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    suspend operator fun invoke(id:Int) = repository.loadAnimeDetail(id)
}