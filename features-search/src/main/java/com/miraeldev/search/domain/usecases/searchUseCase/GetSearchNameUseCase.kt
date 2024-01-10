package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class GetSearchNameUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getSearchName()
}