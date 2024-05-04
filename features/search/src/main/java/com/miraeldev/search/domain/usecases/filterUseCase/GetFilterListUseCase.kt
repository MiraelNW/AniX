package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetFilterListUseCase(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getFilterList()
}