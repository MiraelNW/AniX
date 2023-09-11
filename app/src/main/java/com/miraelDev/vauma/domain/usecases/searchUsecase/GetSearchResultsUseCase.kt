package com.miraelDev.vauma.domain.usecases.searchUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    operator fun invoke() = repository.getSearchResults()

}