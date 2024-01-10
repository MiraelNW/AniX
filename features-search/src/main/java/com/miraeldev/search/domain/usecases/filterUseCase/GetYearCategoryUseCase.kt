package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.FilterAnimeRepository
import javax.inject.Inject

class GetYearCategoryUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    operator fun invoke() = repository.getYearCategory()
}