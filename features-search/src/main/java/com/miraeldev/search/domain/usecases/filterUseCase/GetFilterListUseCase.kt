package com.miraeldev.search.domain.usecases.filterUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class GetFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getFilterList()
}