package com.miraelDev.vauma.domain.usecases

import com.miraelDev.vauma.domain.repository.MainRepository
import me.tatarka.inject.annotations.Inject

@Inject
class GetDarkThemeUseCase(private val repository: MainRepository) {
    operator fun invoke() = repository.getDarkTheme()
}