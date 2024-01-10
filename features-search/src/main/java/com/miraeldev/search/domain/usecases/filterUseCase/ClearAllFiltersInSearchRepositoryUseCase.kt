package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class ClearAllFiltersInSearchRepositoryUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke() = repository.clearAllFilters()
}