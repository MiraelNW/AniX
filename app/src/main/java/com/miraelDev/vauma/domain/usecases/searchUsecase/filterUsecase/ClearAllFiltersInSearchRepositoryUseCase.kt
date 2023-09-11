package com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class ClearAllFiltersInSearchRepositoryUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke() = repository.clearAllFilters()
}