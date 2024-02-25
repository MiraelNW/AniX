package com.miraeldev.animelist.domain.useCases.paging

import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetNamePagingAnimeListUseCase(private val repository: HomeRepository) {
    operator fun invoke() = repository.getPagingNameAnimeList()
}