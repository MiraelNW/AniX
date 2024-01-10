package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class LoadInitialListUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    suspend operator fun invoke() = repository.loadInitialList()

}