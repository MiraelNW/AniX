package com.miraeldev.search.domain.usecases.searchUseCase

import com.miraeldev.search.data.repository.SearchAnimeRepository
import javax.inject.Inject

class SaveNameInAnimeSearchHistoryUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    suspend operator fun invoke(name:String) = repository.saveNameInAnimeSearchHistory(name)

}