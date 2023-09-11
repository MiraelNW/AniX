package com.miraelDev.vauma.domain.usecases.filterUsecase

import com.miraelDev.vauma.domain.repository.FilterAnimeRepository
import javax.inject.Inject

class GetSortByCategoryUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    operator fun invoke() = repository.getSortByCategory()
}