package com.miraeldev.animelist.domain.useCases.paging

import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject
@Inject
class LoadPopularCategoryNextPageUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.loadPopularCategoryNextPage()
}