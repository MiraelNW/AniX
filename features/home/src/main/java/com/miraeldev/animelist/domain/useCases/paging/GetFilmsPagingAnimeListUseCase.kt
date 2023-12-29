package com.miraeldev.animelist.domain.useCases.paging

import com.miraeldev.animelist.data.HomeRepository
import javax.inject.Inject

class GetFilmsPagingAnimeListUseCase @Inject constructor(private val repository: HomeRepository) {
    operator fun invoke() = repository.getPagingFilmsAnimeList()
}