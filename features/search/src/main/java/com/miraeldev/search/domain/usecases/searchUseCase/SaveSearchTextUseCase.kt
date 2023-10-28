package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class SaveSearchTextUseCase @Inject constructor(private val repository: SearchAnimeRepository) {

    operator fun invoke(searchText:String) = repository.saveSearchText(searchText)

}