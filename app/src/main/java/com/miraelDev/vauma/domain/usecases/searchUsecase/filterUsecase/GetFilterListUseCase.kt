package com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getFilterList()
}