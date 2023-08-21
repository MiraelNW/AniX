package com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getFilterList()
}