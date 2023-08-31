package com.miraelDev.hikari.domain.usecases.searchUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class LoadInitialListUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    suspend operator fun invoke() = repository.loadInitialList()

}