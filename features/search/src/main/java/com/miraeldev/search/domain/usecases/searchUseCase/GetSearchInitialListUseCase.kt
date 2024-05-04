package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetSearchInitialListUseCase(val repository: SearchAnimeRepository) {

    operator fun invoke() = repository.getSearchInitialList()
}