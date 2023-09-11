package com.miraelDev.vauma.domain.usecases.searchUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class LoadInitialListUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    suspend operator fun invoke() = repository.loadInitialList()

}