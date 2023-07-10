package com.miraelDev.anix.domain.usecases.filterUsecase

import com.miraelDev.anix.domain.repository.FilterAnimeRepository
import javax.inject.Inject

class ClearAllFiltersInFilterRepository @Inject constructor(val repository: FilterAnimeRepository) {
    suspend operator fun invoke() = repository.clearAllFilters()
}