package com.miraelDev.hikari.domain.usecases.SearchUsecase

import com.miraelDev.hikari.domain.repository.SearchAnimeRepository
import javax.inject.Inject

class SaveSearchTextUseCase @Inject constructor(private val repository: SearchAnimeRepository) {

    operator fun invoke(searchText:String) = repository.saveSearchText(searchText)

}