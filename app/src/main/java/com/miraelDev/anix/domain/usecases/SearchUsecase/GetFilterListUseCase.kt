package com.miraelDev.anix.domain.usecases.SearchUsecase

import com.miraelDev.anix.domain.repository.FilterAnimeRepository
import com.miraelDev.anix.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class GetFilterListUseCase @Inject constructor(val repository: SearchAnimeRepository) {
    operator fun invoke() = repository.getFilterList()
}