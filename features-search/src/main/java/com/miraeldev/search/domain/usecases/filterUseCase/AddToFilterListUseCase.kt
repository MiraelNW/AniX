package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class AddToFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(categoryId: Int, category: String) =
        repository.addToFilterList(categoryId, category)
}