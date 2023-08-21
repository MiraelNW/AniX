package com.miraelDev.hikari.domain.usecases.searchUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetSearchNameUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getSearchName()
}