package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SearchAnimeByNameUseCase(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(name: String) =
        repository.searchAnimeByName(name)
}