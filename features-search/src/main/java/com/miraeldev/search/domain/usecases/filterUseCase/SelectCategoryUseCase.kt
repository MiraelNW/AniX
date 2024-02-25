package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.FilterAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SelectCategoryUseCase(val repository: FilterAnimeRepository) {
    suspend operator fun invoke(categoryId:Int, category: String) =
        repository.selectCategory(categoryId, category)
}