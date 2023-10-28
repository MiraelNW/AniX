package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.FilterAnimeRepository
import javax.inject.Inject

class SelectCategoryUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    suspend operator fun invoke(categoryId:Int, category: String) =
        repository.selectCategory(categoryId, category)
}