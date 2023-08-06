package com.miraelDev.hikari.domain.usecases.SearchUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class SearchAnimeByNameUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(name: String) =
        repository.searchAnimeByName(name)
}