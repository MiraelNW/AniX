package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.FilterAnimeRepository
import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class ClearAllFiltersUseCase @Inject constructor(
    private val filterRepository: FilterAnimeRepository,
    private val searchRepository: SearchAnimeRepository
) {
    suspend operator fun invoke() {
        filterRepository.clearAllFilters()
        searchRepository.clearAllFilters()
    }
}