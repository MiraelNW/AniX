package com.miraelDev.hikari.domain.usecases.searchUsecase.filterUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class AddToFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(categoryId: Int, category: String) =
        repository.addToFilterList(categoryId, category)
}