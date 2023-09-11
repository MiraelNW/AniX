package com.miraelDev.vauma.domain.usecases.searchUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class SaveNameInAnimeSearchHistoryUseCase @Inject constructor(val repository: SearchAnimeRepository) {

    suspend operator fun invoke(name:String) = repository.saveNameInAnimeSearchHistory(name)

}