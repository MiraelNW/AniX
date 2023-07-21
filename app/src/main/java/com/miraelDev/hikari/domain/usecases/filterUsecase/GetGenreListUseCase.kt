package com.miraelDev.hikari.domain.usecases.filterUsecase

import com.miraelDev.hikari.domain.repository.FilterAnimeRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    operator fun invoke() = repository.getGenreList()
}