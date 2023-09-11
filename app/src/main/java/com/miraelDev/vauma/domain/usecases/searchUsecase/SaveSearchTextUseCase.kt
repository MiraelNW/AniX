package com.miraelDev.vauma.domain.usecases.searchUsecase

import com.miraelDev.vauma.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class SaveSearchTextUseCase @Inject constructor(private val repository: SearchAnimeRepository) {

    operator fun invoke(searchText:String) = repository.saveSearchText(searchText)

}