package com.miraeldev.animelist.domain.useCases.homeScreenUseCases

import com.miraeldev.animelist.data.HomeRepository
import me.tatarka.inject.annotations.Inject

@Inject
class LoadDataUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.loadData()
}