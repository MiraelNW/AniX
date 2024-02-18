package com.miraeldev.account.domain

import com.miraeldev.account.data.AccountRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(darkTheme: Boolean) = repository.setDarkTheme(darkTheme)
}