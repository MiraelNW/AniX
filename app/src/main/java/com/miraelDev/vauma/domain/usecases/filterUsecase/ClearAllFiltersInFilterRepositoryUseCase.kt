package com.miraelDev.vauma.domain.usecases.filterUsecase

import com.miraelDev.vauma.domain.repository.FilterAnimeRepository
import javax.inject.Inject

class ClearAllFiltersInFilterRepositoryUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    suspend operator fun invoke() = repository.clearAllFilters()
}