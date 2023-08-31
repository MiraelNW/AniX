package com.miraelDev.hikari.domain.usecases.searchUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    operator fun invoke() = repository.getSearchResults()

}