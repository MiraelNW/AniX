package com.miraelDev.anix.domain.usecases.filterUsecase

import com.miraelDev.anix.domain.repository.FilterAnimeRepository
import com.miraelDev.anix.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetYearCategoryUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    operator fun invoke() = repository.getYearCategory()
}