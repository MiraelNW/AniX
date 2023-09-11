package com.miraelDev.vauma.domain.usecases.searchUsecase.filterUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class RemoveFromFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(categoryId: Int, category: String) =
        repository.removeFromFilterList(categoryId, category)
}