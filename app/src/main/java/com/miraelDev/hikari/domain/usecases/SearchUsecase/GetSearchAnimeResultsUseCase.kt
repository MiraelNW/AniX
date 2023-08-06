package com.miraelDev.hikari.domain.usecases.SearchUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetSearchAnimeResultsUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getAnimeBySearch()
}