package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.FilterAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetYearCategoryUseCase(val repository: FilterAnimeRepository) {
    operator fun invoke() = repository.getYearCategory()
}