package com.miraelDev.vauma.domain.usecases.searchUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class SearchAnimeByNameUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(name: String) =
        repository.searchAnimeByName(name)
}