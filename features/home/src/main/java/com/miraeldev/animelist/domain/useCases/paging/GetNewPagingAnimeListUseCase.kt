package com.miraeldev.animelist.domain.useCases.paging

import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetNewPagingAnimeListUseCase(private val repository: HomeRepository) {
    operator fun invoke() = repository.getPagingNewAnimeList()
}