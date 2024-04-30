package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadAnimeDetailUseCase(val repository: AnimeDetailRepository) {
    suspend operator fun invoke(id: Int) = repository.loadAnimeDetail(id)
}