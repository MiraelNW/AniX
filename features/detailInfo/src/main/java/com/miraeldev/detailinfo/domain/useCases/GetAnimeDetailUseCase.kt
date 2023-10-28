package com.miraeldev.detailinfo.domain.useCases

import com.miraeldev.detailinfo.data.repositories.AnimeDetailRepository
import javax.inject.Inject

class GetAnimeDetailUseCase @Inject constructor(val repository: AnimeDetailRepository) {
    operator fun invoke() = repository.getAnimeDetail()
}