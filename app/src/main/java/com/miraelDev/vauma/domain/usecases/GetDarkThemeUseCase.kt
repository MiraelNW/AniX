package com.miraelDev.vauma.domain.usecases

import com.miraelDev.vauma.domain.repository.MainRepository
import javax.inject.Inject

class GetDarkThemeUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke() = repository.getDarkTheme()
}