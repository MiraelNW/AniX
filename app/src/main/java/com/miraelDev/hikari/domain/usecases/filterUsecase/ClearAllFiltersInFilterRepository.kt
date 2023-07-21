package com.miraelDev.hikari.domain.usecases.filterUsecase

import com.miraelDev.hikari.domain.repository.FilterAnimeRepository
import javax.inject.Inject

class ClearAllFiltersInFilterRepository @Inject constructor(val repository: FilterAnimeRepository) {
    suspend operator fun invoke() = repository.clearAllFilters()
}