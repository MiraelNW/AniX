package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetAnimeDetailUseCase(val repository: AnimeDetailRepository) {
    operator fun invoke() = repository.getAnimeDetail()
}