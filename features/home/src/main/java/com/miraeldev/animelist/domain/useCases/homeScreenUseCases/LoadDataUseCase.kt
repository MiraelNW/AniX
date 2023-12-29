package com.miraeldev.animelist.domain.useCases.homeScreenUseCases

import com.miraeldev.animelist.data.HomeRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.loadData()
}