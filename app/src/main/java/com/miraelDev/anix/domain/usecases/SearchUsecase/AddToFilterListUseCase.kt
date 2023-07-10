package com.miraelDev.anix.domain.usecases.SearchUsecase

import com.miraelDev.anix.domain.models.CategoryModel
import com.miraelDev.anix.domain.repository.FilterAnimeRepository
import com.miraelDev.anix.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class AddToFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    suspend operator fun invoke(categoryId: Int, category: String) =
        repository.addToFilterList(categoryId, category)
}