package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.FilterAnimeRepository
import javax.inject.Inject

class ClearAllFiltersInFilterRepositoryUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    suspend operator fun invoke() = repository.clearAllFilters()
}