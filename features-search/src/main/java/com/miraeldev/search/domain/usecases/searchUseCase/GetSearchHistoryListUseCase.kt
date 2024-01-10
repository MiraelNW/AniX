package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class GetSearchHistoryListUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    operator fun invoke() = repository.getSearchHistoryListFlow()

}