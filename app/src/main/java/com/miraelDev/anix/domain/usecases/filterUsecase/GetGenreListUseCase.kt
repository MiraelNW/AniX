package com.miraelDev.anix.domain.usecases.filterUsecase

import com.miraelDev.anix.domain.repository.FilterAnimeRepository
import com.miraelDev.anix.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetGenreListUseCase @Inject constructor(val repository: FilterAnimeRepository) {
    operator fun invoke() = repository.getGenreList()
}